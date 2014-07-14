package com.ubaza.android;


class Modules {

    private Modules() {
        // No instances
    }


    static Object[] list(App ngswApp) {
        return new Object[]{
                new AppModule(ngswApp),
        };
    }

}
