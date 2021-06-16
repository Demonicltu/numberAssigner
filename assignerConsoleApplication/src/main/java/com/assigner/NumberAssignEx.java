package com.assigner;

import com.assigner.MyNumberAssignerApplication;

public class NumberAssignEx {

    public static void main(String[] args) {
//        args = new String[]{"-api", "http://localhost:8080/", "-if", "input.txt", "-of", "output.txt", "-pass", "pass", "-user", "user"};

        MyNumberAssignerApplication app = new MyNumberAssignerApplication();
        app.run(args);
    }

}
