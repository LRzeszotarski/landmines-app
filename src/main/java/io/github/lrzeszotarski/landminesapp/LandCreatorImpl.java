package io.github.lrzeszotarski.landminesapp;

import org.springframework.stereotype.Service;

import static io.github.lrzeszotarski.landminesapp.FieldState.*;

@Service
public class LandCreatorImpl implements LandCreator {
    @Override
    public FieldState[][] createLand(String[] layout) {
        final int landSize = layout.length;
        final FieldState[][] land = new FieldState[landSize][landSize];
        for (int i = 0; i < landSize; i++) {
            char[] landRow = layout[i].toCharArray();
            for (int j = 0; j < landRow.length; j++) {
                land[i][j] = translate(landRow[j]);
            }
        }
        return land;
    }

    private FieldState translate(char fieldCharacter) {
        return switch (fieldCharacter) {
            case '-': yield UNKNOWN;
            case 'M': yield MINE;
            case '.': yield SAFE;
            default: throw new IllegalStateException("other fields are not allowed");
        };
    }
}
