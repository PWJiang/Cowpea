package com.jiangpingwei.cowpea.data;

import java.util.List;

/**
 * Created by jiangpingwei on 2017/3/8.
 */

public class Data {
    private boolean error;
    private List<Results> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getError() {
        return error;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }
}
