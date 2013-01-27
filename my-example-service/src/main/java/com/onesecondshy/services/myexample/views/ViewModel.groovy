package com.onesecondshy.services.myexample.views

import com.yammer.dropwizard.views.View
import freemarker.template.SimpleHash
import javax.servlet.http.HttpServletRequest
import com.onesecondshy.services.myexample.resources.ViewUtils
import com.onesecondshy.services.myexample.api.User

public class ViewModel extends View {
    private static final DEFAULTS = [
            menuHighlight: 'None',
            author: 'Adam Jordens',
            'appTitle': 'Example App',
            'copyright': '2013 ONE Second Shy Inc.',
            'defaultPageTitle': 'Welcome to the My Example Service Service!'
    ]
    final SimpleHash data = new SimpleHash(DEFAULTS)

    public ViewModel(String viewName, Map<String, Object> extraData) {
        super(viewName)
        data.putAll(extraData)
    }

    static ViewModel createHomeView(Map extraData, HttpServletRequest request) {
        def view = new ViewModel("home.ftl", extraData)
        return ViewUtils.processView(view, request)
    }
}
