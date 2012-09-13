package com.example.helloworld.views

import com.yammer.dropwizard.views.View

public class ViewModel extends View {
    final Map data = new HashMap()

    public ViewModel(String viewName, Map<String, Object> extraData) {
        super(viewName)
        data.putAll(extraData)
    }
}
