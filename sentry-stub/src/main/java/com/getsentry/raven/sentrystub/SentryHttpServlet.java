package com.getsentry.raven.sentrystub;

import com.getsentry.raven.sentrystub.event.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SentryHttpServlet", displayName = "SentryHttpServlet", urlPatterns = "/api/*")
public class SentryHttpServlet extends HttpServlet {
    private SentryStub sentryStub = SentryStub.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Event event = sentryStub.parseEvent(req.getInputStream());
        sentryStub.addEvent(event);
    }
}
