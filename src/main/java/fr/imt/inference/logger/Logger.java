package fr.imt.inference.logger;

import java.util.Arrays;
import java.util.List;

public class Logger {

    private String className;

    public Logger(Class clazz) {
        this.className = prettyClassName(clazz);
    }

    private String prettyClassName(Class clazz){
        String fullName = clazz.getName();
        List<String> exploded = Arrays.asList(fullName.split("\\."));

        StringBuilder stringBuilder = new StringBuilder();
        int size = exploded.size();
        for (int i = 0; i < size; i++) {
            String element = exploded.get(i);
            if (i < size - 1){
                stringBuilder.append(element.charAt(0));
                stringBuilder.append('.');
            }else{
                stringBuilder.append(element);
            }
        }
        return stringBuilder.toString();
    }

    private void printMessage(String level, String message){
        System.out.println(String.format("%5s [%s] %s", level, this.className, message));
    }

    public void debug(String msg){
        this.printMessage("DEBUG", msg);
    }
}
