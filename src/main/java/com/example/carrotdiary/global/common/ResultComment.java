package com.example.carrotdiary.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultComment<T> {
    private int totalComments;
    private T date;
}
