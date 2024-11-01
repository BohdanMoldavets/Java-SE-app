package ua.foxminded.moldavets.project.storage;

import ua.foxminded.moldavets.project.exception.NotExistStorageException;
import ua.foxminded.moldavets.project.model.ContactType;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.model.SectionType;
import ua.foxminded.moldavets.project.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    //"SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE uuid = ?"
    //SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid LEFT JOIN section s ON r.uuid = s.resume_uuid WHERE uuid = 'uuid1'
    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                do {
                    resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                            (resultSet.getString("value")));
                } while (resultSet.next());
                return resume;
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute (connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY (full_name,uuid)")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Resume> resumeList = new ArrayList<>();
                while(resultSet.next()) {
                    Resume resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
                    resume.addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                    resumeList.add(resume);
                }
                return resumeList;
            }
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, entry.getKey().name());
                    preparedStatement.setString(3, entry.getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.execute();
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(connection -> {
           try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
               preparedStatement.setString(1, uuid);
               preparedStatement.execute();
               return null;
           }
        });
    }

    @Override
    public void clear() {
        //sqlHelper.execute("DELETE FROM resume");
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact")) {
                preparedStatement.execute();
            }
            return null;
        });
    }

    @Override
    public void setStorageLimit(int storageLimit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getStorageLimit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : -1;
        });
    }
}
