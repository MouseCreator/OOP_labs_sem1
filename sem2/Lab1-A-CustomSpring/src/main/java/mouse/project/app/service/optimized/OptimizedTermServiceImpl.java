package mouse.project.app.service.optimized;

import jakarta.persistence.EntityNotFoundException;
import mouse.project.app.defines.Progress;
import mouse.project.app.dto.progress.TermProgressPair;
import mouse.project.app.dto.progress.TermProgressUpdates;
import mouse.project.app.dto.term.TermWithProgressResponseDTO;
import mouse.project.app.exception.UpdateException;
import mouse.project.app.mapper.TermMapper;
import mouse.project.app.model.StudySet;
import mouse.project.app.model.Term;
import mouse.project.app.model.User;
import mouse.project.app.model.UserTerm;
import mouse.project.app.dao.StudySetRepository;
import mouse.project.app.dao.TermRepository;
import mouse.project.app.dao.UserRepository;
import mouse.project.app.dao.UserTermRepository;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;

import java.util.*;

@Service
public class OptimizedTermServiceImpl implements OptimizedTermService {

    private final StudySetRepository studySetRepository;
    private final UserTermRepository repository;
    private final TermRepository termRepository;
    private final TermMapper termMapper;
    private final UserRepository userRepository;
    @Auto
    public OptimizedTermServiceImpl(StudySetRepository studySetRepository,
                                    UserTermRepository repository,
                                    TermMapper termMapper,
                                    TermRepository termRepository,
                                    UserRepository userRepository) {
        this.studySetRepository = studySetRepository;
        this.repository = repository;
        this.termRepository = termRepository;
        this.termMapper = termMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<TermWithProgressResponseDTO> updateAll(TermProgressUpdates updates) {
        List<TermProgressPair> updatesList = updates.getUpdatesList();
        Long userId = updates.getUserId();
        List<UserTerm> userTerms = createUserTerms(userId, updatesList);
        Map<Long, UserTerm> saved = new HashMap<>();
        userTerms.forEach(ut -> {
            UserTerm save = repository.save(ut);
            saved.put(ut.getTerm().getId(), save);
        });
        List<Long> termIds = updates.getUpdatesList().stream().map(TermProgressPair::getTerm).toList();
        List<Term> terms = termRepository.findAllByIds(termIds);

        return toTermsWithProgress(terms, saved, userId);
    }

    private List<TermWithProgressResponseDTO> toTermsWithProgress(List<Term> terms,
                                                                  Map<Long, UserTerm> saved,
                                                                  Long userId) {
        List<TermWithProgressResponseDTO> result = new ArrayList<>();
        for (Term term : terms) {
            UserTerm userTerm = saved.get(term.getId());
            if (userTerm == null) {
                throw new UpdateException("No progress defined for user " + userId + " and term " + term.getId());
            }
            String progress = userTerm.getProgress();
            TermWithProgressResponseDTO responseWithProgress = termMapper.toResponseWithProgress(term, progress);
            result.add(responseWithProgress);
        }
        return result;
    }

    private TermsOfSet getTermsFromSet(Long studySetId) {
        Optional<StudySet> byId = studySetRepository.findAllByIdWithTerms(studySetId);
        if(byId.isEmpty()) {
            throw new EntityNotFoundException("No study set with id " + studySetId + " found");
        }
        return new TermsOfSet(byId.get().getTerms());
    }
    private static class TermsOfSet {
        private final List<Term> terms;
        public TermsOfSet(List<Term> terms) {
            this.terms = terms;
        }
        public List<Term> terms() {
            return new ArrayList<>(terms);
        }
        public List<Long> ids() {
            return terms.stream().map(Term::getId).toList();
        }
    }

    private List<UserTerm> createUserTerms(Long userId, List<TermProgressPair> updatesList) {
        return updatesList.stream().map(u -> {
            UserTerm userTerm = new UserTerm();
            userTerm.setProgress(u.getProgress());
            userTerm.setUser(getUser(userId));
            userTerm.setTerm(new Term(u.getTerm()));
            return userTerm;
        }).toList();
    }

    @Override
    public List<TermWithProgressResponseDTO> getForUserFromStudySet(Long userId, Long studySetId) {
        TermsOfSet termsFromSet = getTermsFromSet(studySetId);
        Map<Long, UserTerm> map = getProgressMap(userId, termsFromSet.ids());
        if (map.isEmpty() && !termsFromSet.terms().isEmpty()) {
            throw new EntityNotFoundException("No progress defined for user " + userId + " and set " + studySetId);
        }
        return toTermsWithProgress(termsFromSet.terms(), map, userId);
    }

    @Override
    public List<TermWithProgressResponseDTO> initializeProgress(Long userId, Long studySetId) {
        User user = getUser(userId);
        TermsOfSet termsFromSet = getTermsFromSet(studySetId);
        List<Term> terms = termsFromSet.terms();
        List<TermWithProgressResponseDTO> result = new ArrayList<>();
        terms.forEach(t -> {
            UserTerm userTerm = new UserTerm();
            userTerm.setUser(user);
            userTerm.setProgress(Progress.UNFAMILIAR);
            userTerm.setTerm(t);
            UserTerm saved = repository.save(userTerm);
            result.add(termMapper.toResponseWithProgress(t, saved.getProgress()));
        });
        return result;
    }
    private User getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }
        return user.get();
    }

    private Map<Long, UserTerm> getProgressMap(Long userId, List<Long> idList) {
        List<UserTerm> userTerm = repository.findByUserAndTerms(userId, idList);
        Map<Long, UserTerm> map = new HashMap<>();
        userTerm.forEach(ut -> map.put(ut.getTerm().getId(), ut));
        return map;
    }

    @Override
    public List<TermWithProgressResponseDTO> resetProgress(Long userId, Long studySetId) {
        TermsOfSet termsFromSet = getTermsFromSet(studySetId);
        Map<Long, UserTerm> map = getProgressMap(userId, termsFromSet.ids());
        List<TermWithProgressResponseDTO> result = new ArrayList<>();
        termsFromSet.terms().forEach(t -> {
            UserTerm ut = map.get(t.getId());
            ut.setProgress(Progress.UNFAMILIAR);
            UserTerm save = repository.save(ut);
            result.add(termMapper.toResponseWithProgress(t, save.getProgress()));
        });
        return result;
    }

    @Override
    public void removeProgress(Long userId, Long studySetId) {
        TermsOfSet termsFromSet = getTermsFromSet(studySetId);
        repository.deleteByUserAndTerms(userId, termsFromSet.ids());
    }

    @Override
    public int getUserProgress(Long userId, Long studySetId) {
        TermsOfSet termsFromSet = getTermsFromSet(studySetId);
        return (int) repository.findByUserAndTerms(userId, termsFromSet.ids())
                .stream().filter(t -> t.getProgress().equals(Progress.LEARNED)).count();
    }
}
