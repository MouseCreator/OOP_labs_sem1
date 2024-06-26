package mouse.project.app.dao;

import mouse.project.app.model.Term;
import mouse.project.app.model.User;
import mouse.project.app.model.UserTerm;
import mouse.project.app.model.UserTermModel;
import mouse.project.lib.data.executor.Executor;
import mouse.project.lib.data.utils.DaoUtils;
import mouse.project.lib.ioc.annotation.After;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class UserTermRepositoryImpl implements UserTermRepository {
    private final Executor executor;
    private final DaoUtils daoUtils;
    private UserRepository userRepository;
    private TermRepository termRepository;
    @Auto
    public UserTermRepositoryImpl(Executor executor, DaoUtils daoUtils) {
        this.executor = executor;
        this.daoUtils = daoUtils;
    }
    @After
    public void setRepositories(UserRepository userRepository, TermRepository termRepository) {
        this.userRepository = userRepository;
        this.termRepository = termRepository;
    }

    @Override
    public List<UserTerm> findAll() {
        return executor.executeQuery(
                "SELECT ut FROM users_terms ut " +
                    "INNER JOIN users u ON ut.user_id = u.id" +
                    "INNER JOIN terms t ON ut.term_id = t.id" +
                    "WHERE u.deletedAt IS NULL AND t.deletedAt IS NULL"
        ).adjustedList(UserTermModel.class).map(this::fromModel).get();
    }

    private UserTerm fromModel(UserTermModel userTermModel) {
        String progress = userTermModel.getProgress();
        Long userId = userTermModel.getUserId();
        Long termId = userTermModel.getTermId();

        UserTerm userTerm = new UserTerm();
        userTerm.setProgress(progress);

        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(userTerm::setUser);

        Optional<Term> termOptional = termRepository.findById(termId);
        termOptional.ifPresent(userTerm::setTerm);

        return userTerm;
    }

    @Override
    public void deleteByUserAndTerms(Long id, List<Long> termIds) {
        String qm = daoUtils.qMarksList(termIds);
        String sql = String.format(
                "DELETE FROM users_terms ut WHERE ut.user_id = ? AND ut.term_id IN %s", qm
        );
        List<Long> args = new ArrayList<>(termIds);
        args.add(0, id);
        executor.executeQuery(sql, args);
    }

    @Override
    public UserTerm save(UserTerm model) {
        Long termId = model.getTerm().getId();
        Long userId = model.getUser().getId();
        String progress = model.getProgress();
        return executor.executeQuery("INSERT INTO users_terms (term_id, user_id, progress) VALUES (?,?,?)",
                termId, userId, progress)
                .adjusted(UserTermModel.class)
                .map(t -> new UserTerm(model.getUser(), model.getTerm(), t.getProgress())).get();
    }

    @Override
    public List<UserTerm> findByUserAndTerms(Long userId, List<Long> termIds) {
        String qm = daoUtils.qMarksList(termIds);
        String sql = String.format(
                "SELECT ut " +
                "FROM users_terms ut " +
                "INNER JOIN users u ON ut.user_id = u.id" +
                "INNER JOIN terms t ON ut.term_id = t.id" +
                "WHERE u.id = ? AND t.id IN %s AND " +
                "u.deletedAt IS NULL AND t.deletedAt IS NULL", qm
        );
        List<Long> args = new ArrayList<>(termIds);
        return executor.executeQuery(sql, args)
                .adjustedList(UserTermModel.class)
                .map(this::fromModel)
                .get();
    }
}
