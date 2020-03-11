package io.github.lrzeszotarski.landminesapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class FieldCoordinates {
    private int x;

    private int y;
}
