import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testCases = in.nextInt();
        List<SnakesAndLaddersEntity> testCasesList = new LinkedList<>();
        readData(in, testCases, testCasesList);
        runTestCases(testCasesList);
    }

    private static void runTestCases(List<SnakesAndLaddersEntity> testCasesList) {
        for (SnakesAndLaddersEntity snakesAndLaddersEntity : testCasesList) {
            findShortestPath(snakesAndLaddersEntity);
        }
    }

    private static void findShortestPath(SnakesAndLaddersEntity snakesAndLaddersEntity) {
        int[] positions = getPositionWithoutLaddersAndSnakes(snakesAndLaddersEntity);

        List<Integer> alreadyVisited = new ArrayList<>();
        boolean gameCondition = true;

        int rollsCount = 0;
        List<Integer> currentLevel = new ArrayList<>();
        //first element is 1, so we need to start from 2
        currentLevel.add(2);
        List<Integer> tempCurrentLvl = new ArrayList<>();
        while (gameCondition) {
            rollsCount++;
            //condition when we have not reach any progress
            if(tempCurrentLvl.containsAll(currentLevel)){
                System.out.println(-1);
                break;
            }
            tempCurrentLvl.clear();
            tempCurrentLvl.addAll(currentLevel);
            currentLevel.clear();
            //dirty and straight forward implementation
            // could be done better, without already visited positions
            for (Integer startPoint : tempCurrentLvl) {
                gameCondition = isGameCondition(alreadyVisited, rollsCount, currentLevel, startPoint, positions);
                if(!gameCondition){
                    break;
                }
            }
        }
    }

    /**
     * Method replaces every ladder and snake
     */
    private static int[] getPositionWithoutLaddersAndSnakes(SnakesAndLaddersEntity snakesAndLaddersEntity) {
        int[] positions = new int[100];
        for (int i = 0; i < 100; i++) {
            positions[i] = i + 1;
        }
        List<StartEnd> ladders = snakesAndLaddersEntity.ladders;
        List<StartEnd> snakes = snakesAndLaddersEntity.snakes;

        for (StartEnd ladder : ladders) {
            positions[ladder.start] = ladder.end;
        }
        for (StartEnd snake : snakes) {
            positions[snake.start] = snake.end;
        }
        return positions;
    }

    private static void readData(Scanner in, int testCases, List<SnakesAndLaddersEntity> testCasesList) {
        for (int i = 0; i < testCases; i++) {
            List<StartEnd> laddersList = new LinkedList<>();
            int ladders = in.nextInt();
            for (int j = 0; j < ladders; j++) {
                int start = in.nextInt();
                int end = in.nextInt();
                StartEnd entity = new StartEnd(start, end);
                laddersList.add(entity);
            }
            List<StartEnd> snakesList = new LinkedList<>();
            int snakes = in.nextInt();
            for (int j = 0; j < snakes; j++) {
                int start = in.nextInt();
                int end = in.nextInt();
                StartEnd entity = new StartEnd(start, end);
                snakesList.add(entity);
            }
            testCasesList.add(new SnakesAndLaddersEntity(laddersList, snakesList));
        }
    }

    private static boolean isGameCondition(List<Integer> alreadyVisited, int count, List<Integer> currentLevel, Integer startPoint, int[] positions) {
        for (int i = 1; i <= 6; i++) {
            int position = startPoint;
            position += i;
            //check boundaries
            if(position < 100){
                position = positions[position - 1];
            }
            if (position == 100) {
                System.out.println(count);
                return false;
            }
            if (!alreadyVisited.contains(position)) {
                currentLevel.add(position);
                alreadyVisited.add(position);
            }
        }
        return true;
    }
    //classes for help, can be easily refactored and removed
    private static class StartEnd {
        int start;
        int end;

        public StartEnd(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private static class SnakesAndLaddersEntity {
        List<StartEnd> ladders;
        List<StartEnd> snakes;

        public SnakesAndLaddersEntity(List<StartEnd> ladders, List<StartEnd> snakes) {
            this.ladders = ladders;
            this.snakes = snakes;
        }
    }
}
