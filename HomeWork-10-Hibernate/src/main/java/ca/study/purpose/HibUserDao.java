package ca.study.purpose;

import java.util.List;
import java.util.Optional;

public interface HibUserDao {
    void create(HibUser obj);
    void update(HibUser obj);
    Optional<HibUser> load(long id, Class<HibUser> clazz);
    List<HibUser> getHibUsers();
}
