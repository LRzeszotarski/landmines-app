package io.github.lrzeszotarski.landminesapp;

import org.springframework.stereotype.Service;

import java.util.*;

import static io.github.lrzeszotarski.landminesapp.FieldState.MINE;
import static io.github.lrzeszotarski.landminesapp.FieldState.SAFE;

@Service
public class LandMinesImpl implements LandMines {

    private final LandCreator landCreator;

    public LandMinesImpl(LandCreator landCreator) {
        this.landCreator = landCreator;
    }

    @Override
    public int numClear(String[] layout) {
        final FieldState[][] land = landCreator.createLand(layout);
        final Queue<FieldCoordinates> availableFields = new LinkedList();
        final Set<FieldCoordinates> checkedFields = new HashSet();

        final FieldCoordinates startingPoint = new FieldCoordinates(0, 0);
        availableFields.add(startingPoint);
        land[startingPoint.x()][startingPoint.y()] = SAFE;

        while (!availableFields.isEmpty()) {
            final FieldCoordinates currentCoordinates = availableFields.poll();
            checkedFields.add(currentCoordinates);
            checkNorth(currentCoordinates, land, availableFields, checkedFields);
            checkSouth(currentCoordinates, land, availableFields, checkedFields);
            checkWest(currentCoordinates, land, availableFields, checkedFields);
            checkEast(currentCoordinates, land, availableFields, checkedFields);
        }
        return checkedFields.size();
    }

    private void checkEast(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        if (!eastExists(currentCoordinates, land.length)) {
            return;
        }
        if (eastContainsMine(currentCoordinates, land)) {
            return;
        }
        markEastAsSafe(currentCoordinates, land, availableFields, checkedFields);
    }

    private void checkWest(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        if (!westExists(currentCoordinates)) {
            return;
        }
        if (westContainsMine(currentCoordinates, land)) {
            return;
        }
        markWestAsSafe(currentCoordinates, land, availableFields, checkedFields);
    }

    private void checkSouth(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        if (!southExists(currentCoordinates, land.length)) {
            return;
        }
        if (southContainsMine(currentCoordinates, land)) {
            return;
        }
        markSouthAsSafe(currentCoordinates, land, availableFields, checkedFields);
    }

    private void checkNorth(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        if (!northExists(currentCoordinates)) {
            return;
        }
        if (northContainsMine(currentCoordinates, land)) {
            return;
        }
        markNorthAsSafe(currentCoordinates, land, availableFields, checkedFields);
    }

    private void markEastAsSafe(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        for (int x = currentCoordinates.x() + 1; x < land.length; x++) {
            final FieldCoordinates pointedCoordinates = new FieldCoordinates(x, currentCoordinates.y());
            markFieldIfNotMarked(land, availableFields, checkedFields, pointedCoordinates);
        }
    }

    private void markWestAsSafe(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        for (int x = 0; x < currentCoordinates.x(); x++) {
            final FieldCoordinates pointedCoordinates = new FieldCoordinates(x, currentCoordinates.y());
            markFieldIfNotMarked(land, availableFields, checkedFields, pointedCoordinates);
        }
    }

    private void markSouthAsSafe(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        for (int y = currentCoordinates.y() + 1; y < land.length; y++) {
            final FieldCoordinates pointedCoordinates = new FieldCoordinates(currentCoordinates.x(), y);
            markFieldIfNotMarked(land, availableFields, checkedFields, pointedCoordinates);
        }
    }

    private void markNorthAsSafe(FieldCoordinates currentCoordinates, FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields) {
        for (int y = 0; y < currentCoordinates.y(); y++) {
            final FieldCoordinates pointedCoordinates = new FieldCoordinates(currentCoordinates.x(), y);
            markFieldIfNotMarked(land, availableFields, checkedFields, pointedCoordinates);
        }
    }

    private boolean eastExists(FieldCoordinates currentCoordinates, int length) {
        return currentCoordinates.x() != length - 1;
    }

    private boolean westExists(FieldCoordinates currentCoordinates) {
        return currentCoordinates.x() != 0;
    }

    private boolean southExists(FieldCoordinates currentCoordinates, int length) {
        return currentCoordinates.y() != length - 1;
    }

    private boolean northExists(FieldCoordinates currentCoordinates) {
        return currentCoordinates.y() != 0;
    }

    private boolean eastContainsMine(FieldCoordinates currentCoordinates, FieldState[][] land) {
        return Arrays.asList(land[currentCoordinates.y()]).subList(currentCoordinates.x() + 1, land.length).contains(MINE);
    }

    private boolean westContainsMine(FieldCoordinates currentCoordinates, FieldState[][] land) {
        return Arrays.asList(land[currentCoordinates.y()]).subList(0, currentCoordinates.x()).contains(MINE);
    }

    private boolean southContainsMine(FieldCoordinates currentCoordinates, FieldState[][] land) {
        for (int y = currentCoordinates.y() + 1; y < land.length; y++) {
            if (land[y][currentCoordinates.x()] == MINE) {
                return true;
            }
        }
        return false;
    }

    private boolean northContainsMine(FieldCoordinates currentCoordinates, FieldState[][] land) {
        for (int y = 0; y < currentCoordinates.y(); y++) {
            if (land[y][currentCoordinates.x()] == MINE) {
                return true;
            }
        }
        return false;
    }

    private void markFieldIfNotMarked(FieldState[][] land, Queue<FieldCoordinates> availableFields, Set<FieldCoordinates> checkedFields, FieldCoordinates pointedCoordinates) {
        if (!checkedFields.contains(pointedCoordinates) && !availableFields.contains(pointedCoordinates)) {
            land[pointedCoordinates.y()][pointedCoordinates.x()] = SAFE;
            availableFields.add(pointedCoordinates);
        }
    }
}
