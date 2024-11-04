package ua.foxminded.moldavets.project.web;

import ua.foxminded.moldavets.project.Config;
import ua.foxminded.moldavets.project.model.ContactType;
import ua.foxminded.moldavets.project.model.Resume;
import ua.foxminded.moldavets.project.model.SectionType;
import ua.foxminded.moldavets.project.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResumeServlet extends HttpServlet {

    private final Storage storage = Config.get().getStorage();
    private final String tabulatorPattern = "\t\t\t";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String uuid = request.getParameter("uuid");
        try (PrintWriter printWriter = response.getWriter()) {
            ArrayList<Resume> resumes = new ArrayList<>();
            if (uuid != null) {
                resumes.add(storage.get(uuid));
                createTable(printWriter, resumes);
            } else {
                resumes.addAll(storage.getAllSorted());
                createTable(printWriter, resumes);
            }
       }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void createTable(PrintWriter printWriter, List<Resume> resumes) {
        printWriter.println(String.format("""
                    <!DOCTYPE html>
                    <html>
                        <head>
                            <title>Resumes</title>
                            <link rel="stylesheet" type="text/css" href="css/style.css">
                        </head>
                        <body>
                            <table border="1">
                                <tr>
                                    %s
                                </tr>
                                    %s
                            </table>
                        </body>
                    </html>
                    """,prepareTable(SectionType.values(),
                        ContactType.values()),
                resumeTable(resumes,
                        SectionType.values(),
                        ContactType.values())));
    }

    private String prepareTable(SectionType[] sectionTypes, ContactType[] contactTypes) {
        return "\t<td>UUID</td>\n" + tabulatorPattern + "\t<td>FULL NAME</td>\n\t" + tabulatorPattern +
                tableCells(sectionTypes) +
                tableCells(contactTypes);
    }

    private String tableCells (Enum[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Enum e : array) {
            stringBuilder.append(wrapStringToTableCell(e.name()));
        }
        return stringBuilder.toString();
    }

    private String wrapStringToTableCell(Object o) {
        return String.format("<td>%s</td>\n\t" + tabulatorPattern,o);
    };

    private String resumeTable(List<Resume> resumes, SectionType[] sectionTypes, ContactType[] contactTypes) {
        StringBuilder stringBuilder = new StringBuilder();
        if(!resumes.isEmpty()) {
            for(Resume resume : resumes) {
                stringBuilder.append("<tr>\n\t")
                        .append(tabulatorPattern)
                        .append(wrapStringToTableCell(String.format("<a href='resume?uuid=%s'>%s</a>",resume.getUuid().trim(),resume.getUuid().trim())))
                        .append(wrapStringToTableCell(resume.getFullName()));
                for(SectionType sectionType : sectionTypes) {
                    if(resume.getSection(sectionType) != null) {
                        stringBuilder
                                .append(wrapStringToTableCell(resume.getSection(sectionType)));
                    } else {
                        stringBuilder.append(wrapStringToTableCell("-"));
                    }
                }
                for(ContactType contactType : contactTypes) {
                    if(resume.getContact(contactType) != null) {
                        stringBuilder.append(wrapStringToTableCell(resume.getContact(contactType)));
                    }
                    else {
                        stringBuilder.append(wrapStringToTableCell("-"));
                    }
                }
                stringBuilder.append("</tr>\n\t\t");
            }
        }
        return stringBuilder.toString();
    }
}
