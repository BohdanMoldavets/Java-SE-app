package ua.foxminded.moldavets.project.storage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.moldavets.project.Config;
import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.model.ContactType;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.model.SectionType;
import ua.foxminded.moldavets.project.model.TextSection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SqlStorageTest {


    private static Connection connection;

    private static final File PROPS = new File("C:\\Users\\steam\\IdeaProjects\\Java-SE-app\\config\\resumes.properties");
    private static final Properties PROPERTIES = new Properties();

    private final Resume resume = new Resume("123","Test");

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        try(FileInputStream inputStream = new FileInputStream(PROPS)) {
            PROPERTIES.load(inputStream);
            connection = DriverManager.getConnection(PROPERTIES.getProperty("db.url"), PROPERTIES.getProperty("db.user"), PROPERTIES.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid file");
        }
    }

    @AfterEach
    void tearDownAfterClass() {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save_shouldSaveResumeIntoSqlStorage_whenResumeContainsOnlyUuidAndFullName() throws Exception {
        Config.get().getStorage().save(resume);
        Resume actual;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(resume.getUuid());
            }
            actual = new Resume(resultSet.getString("uuid").trim(),
                    resultSet.getString("full_name"));
        }
        assertEquals(resume,actual);
    }

    @Test
    void save_shouldSaveResumeIntoSqlStorage_whenResumeContainsUuidFullNameAndContacts() throws Exception {
        Config.get().getStorage().save(resume);
        Resume actual;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid,type, value) VALUES (?,?,?)")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, "EMAIL");
            preparedStatement.setString(3, "example@example.com");
            preparedStatement.execute();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(resume.getUuid());
            }
            actual = new Resume(resultSet.getString("uuid").trim(),
                    resultSet.getString("full_name")
            );
            actual.addContact(ContactType.valueOf(resultSet.getString("type")),
                    resultSet.getString("value")
            );
        }
        assertEquals(resume,actual);
    }

    @Test
    void save_shouldSaveResumeIntoSqlStorage_whenResumeContainsUuidFullNameAndSection() throws Exception {
        Config.get().getStorage().save(resume);
        Resume actual;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (resume_uuid,type, content) VALUES (?,?,?)")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, "PERSONAL");
            preparedStatement.setString(3, "Personal qualities");
            preparedStatement.execute();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r LEFT JOIN section s ON r.uuid = s.resume_uuid WHERE r.uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(resume.getUuid());
            }
            actual = new Resume(resultSet.getString("uuid").trim(),
                    resultSet.getString("full_name"));
            actual.addSection(SectionType.valueOf(resultSet.getString("type")),
                    new TextSection(resultSet.getString("content"))
            );
        }
        assertEquals(resume,actual);
    }

    @Test
    void get_shouldReturnValidResume_whenStorageContainsValidResume () throws Exception {
        Resume expected = new Resume("123","Test");
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid,full_name) VALUES (?,?)")) {
            preparedStatement.setString(1, "123");
            preparedStatement.setString(2, "Test");
            preparedStatement.execute();
        }
        assertEquals(expected, Config.get().getStorage().get(resume.getUuid()));
    }

    @Test
    void get_shouldReturnValidResumeWithContacts_whenStorageContainsValidResumeWithContacts () throws Exception {
        Resume expected = new Resume("123","Test");
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid,full_name) VALUES (?,?)")) {
            preparedStatement.setString(1, "123");
            preparedStatement.setString(2, "Test");
            preparedStatement.execute();
        }
        resume.addContact(ContactType.EMAIL,"example@example.com");

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid,type, value) VALUES (?,?,?)")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, "EMAIL");
            preparedStatement.setString(3, "example@example.com");
            preparedStatement.execute();
        }

        assertEquals(expected,
                Config.get().getStorage().get(resume.getUuid()));
    }

    @Test
    void getAllSorted_shouldReturnTwoResumes_whenStorageContainsTwoResumes() throws Exception {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid,full_name) VALUES (?,?)")) {
            preparedStatement.setString(1, "1234");
            preparedStatement.setString(2, "Test");
            preparedStatement.execute();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid,full_name) VALUES (?,?)")) {
            preparedStatement.setString(1, "123");
            preparedStatement.setString(2, "Test1");
            preparedStatement.execute();
        }
        List<Resume> expected = new ArrayList<>();
        expected.add(new Resume("1234","Test"));
        expected.add(new Resume("123","Test1"));
        List<Resume> actual = Config.get().getStorage().getAllSorted().stream()
                .map(resume1 -> new Resume(resume1.getUuid().trim(),resume1.getFullName()))
                .collect(Collectors.toList());

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?; DELETE FROM resume WHERE uuid = ?")) {
            preparedStatement.setString(1, "123");
            preparedStatement.setString(2, "1234");
            preparedStatement.execute();
        }
        assertEquals(expected,actual);
    }

    @Test
    void delete_shouldDeleteResumeFromStorage_whenStorage() throws Exception {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid,full_name) VALUES (?,?)")) {
            preparedStatement.setString(1, "123");
            preparedStatement.setString(2, "Test");
            preparedStatement.execute();
        }
        Config.get().getStorage().delete(resume.getUuid());
        Exception exception = assertThrows(NotExistStorageException.class,
                () -> Config.get().getStorage().get(resume.getUuid()));
        assertEquals("Resume 123 does not exist",exception.getMessage());
    }

    //TODO
    @Test
    void clear_shouldDeleteAllResumesFromStorage_whenStorageNotEmpty() throws Exception {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid,full_name) VALUES (?,?)")) {
            preparedStatement.setString(1, "123");
            preparedStatement.setString(2, "Test");
            preparedStatement.execute();
        }
        Config.get().getStorage().clear();
        Thread.sleep(5000);
        for (Resume resume : Config.get().getStorage().getAllSorted()) {
            System.out.println(resume);
        }
        //assertEquals(0,Config.get().getStorage().getSize());
    }

}