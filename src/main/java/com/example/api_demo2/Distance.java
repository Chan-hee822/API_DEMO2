package com.example.api_demo2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;

@Getter
@Setter
@AllArgsConstructor
public class Distance implements Cloneable{
        private double distance;
        private Point2D location;

        public void sort(){
                sort();
        /*
        Comparator를 넘겨주지 않을 경우 해댱 객체의 Comparable에 구현한 정렬방식을 사용.
        만약 구현되어있지 않으면 에러발생
        만약 구현되어 있고 null로 넘기면 Arrays.sort()각 객체의 compareTo 메소드에 정의된 방식대로 정렬.
         */
        }
}
