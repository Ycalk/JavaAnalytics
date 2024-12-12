package source;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import source.entities.StudentScore;
import source.entities.TopicInfo;

import java.util.List;

public class DataBase {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("unit");

    public void saveStudent(source.entities.Region studentRegion,
                            source.entities.Student student,
                            List<TopicInfo> topics,
                            List<StudentScore> scores) {
        var entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        var currentTransaction = entityManager.getTransaction();
        currentTransaction.begin();
        try{
            if (studentRegion != null)
                entityManager.merge(studentRegion);
            if (student != null)
                entityManager.persist(student);
            if (topics != null)
                for (var topic : topics){
                    if (topic != null)
                        entityManager.merge(topic);
                }
            if (scores != null)
                for (var score : scores){
                    if (score != null)
                        entityManager.merge(score);
                }
            currentTransaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public List<source.entities.Student> getStudents(){
        var entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        var query = entityManager.createQuery("SELECT s FROM Student s", source.entities.Student.class);
        return query.getResultList();
    }
}
