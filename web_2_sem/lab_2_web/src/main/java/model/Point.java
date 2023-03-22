package model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Point {
    private Integer attampt;
    @NonNull 
    private Double x;
    @NonNull 
    private Double y;
    @NonNull 
    private Double r;
    @NonNull 
    private Boolean result;
    @NonNull
    private String time;
    private float duration;
}
