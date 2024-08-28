package ru.aston3.hibernate.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston3.hibernate.model.BillingDetails;
import ru.aston3.hibernate.repository.BillingDetailsRepository;
import ru.aston3.hibernate.repository.impl.BillingDetailsRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/billing/*"})
public class BillingDetailsServlet extends HttpServlet {
    private final BillingDetailsRepository billingDetailsRepository = BillingDetailsRepositoryImpl.getInstance();
    private final ObjectMapper objectMapper;

    public BillingDetailsServlet() {
        this.objectMapper = new ObjectMapper();
    }

    private static void setJsonHeader(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader postData = req.getReader();
        String line;
        while ((line = postData.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            if ("all".equals(pathPart[1])) {
                List<BillingDetails> billingDetails = billingDetailsRepository.findAll();
                resp.setStatus(HttpServletResponse.SC_OK);
                responseAnswer = objectMapper.writeValueAsString(billingDetails);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String json = getJson(req);
        String responseAnswer = null;
        Optional<BillingDetails> billingDetailsResponse;
        try {
            billingDetailsResponse = Optional.ofNullable(objectMapper.readValue(json, BillingDetails.class));
            BillingDetails billingDetails = billingDetailsResponse.orElseThrow(IllegalArgumentException::new);
            responseAnswer = objectMapper.writeValueAsString(billingDetailsRepository.save(billingDetails));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Incorrect billing Object.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setJsonHeader(resp);
        String responseAnswer = "";
        try {
            String[] pathPart = req.getPathInfo().split("/");
            Integer billingDetailsId = Integer.parseInt(pathPart[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            billingDetailsRepository.deleteById(billingDetailsId);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseAnswer = "Bad request.";
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(responseAnswer);
        printWriter.flush();
    }
}
