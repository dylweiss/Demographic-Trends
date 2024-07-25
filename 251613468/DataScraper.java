
package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataScraper {

    private static  Map<String, List<String>> borderInfo;

    static {
        try {
            borderInfo = getBorderInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document connectToWebsite(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public static Map<String, List<String>> borderInfoGetter() {
        return borderInfo;
    }

    public static String[][] getStateInfo() throws IOException {
        String mainURL = "https://state.1keydata.com/";
        Document doc = connectToWebsite(mainURL);
        String urlBase = "https://state.1keydata.com/";
        String[][] stateInfo = new String[50][5];
        int row = 0;
        for (int i = 1; i <= 4; i++) {
            String columnId = "#col" + i;
            Elements links = doc.select(columnId + " a");

            for (Element link : links) {
                String stateURL = urlBase + link.attr("href");
                String stateName = link.text();

                Document stateDoc = connectToWebsite(stateURL);
                String population = "";
                String stateSize = "";
                String numberOfCounties = "";
                String electoralVotes = "";

                Elements tables = stateDoc.select("table.content");

                // Check if there are at least two tables
                if (tables.size() >= 2) {
                    // Get the second table
                    Element tableOne = tables.get(0);

                    Elements tableOneRows = tableOne.select("tr");
                    for (Element tableOneRow : tableOneRows) {
                        if (tableOneRow.select("td.fact_title b a").text().equals("State Population (2020)")) {
                            population = tableOneRow.select("td:eq(1)").text().replaceAll(",", "");
                            break; // Exit loop since we found the population information
                        } else if (tableOneRow.select("td.fact_title b a").text().equals("State Size")) {
                            stateSize = extractTotalArea(tableOneRow.select("td:eq(1)").text());
                        }
                        // Extract number of counties
                        else if (tableOneRow.select("td.fact_title b").text().equals("Number of Counties")) {
                            numberOfCounties = tableOneRow.select("td:eq(1)").text();
                        }
                    }

                    Element tableTwo = tables.get(1);
                    Elements tableTwoRows = tableTwo.select("tr");
                    for (Element tableTwoRow : tableTwoRows) {
                        if (tableTwoRow.select("td.fact_title b a").text().equals("Electoral Votes")) {
                            electoralVotes = tableTwoRow.select("td:eq(1)").text();
                            break;
                        }
                    }
                } else {
                    System.out.println("There are less than two tables on the page.");
                }

                stateInfo[row][0] = stateName;
                stateInfo[row][1] = population;
                stateInfo[row][2] = stateSize;
                stateInfo[row][3] = numberOfCounties;
                stateInfo[row][4] = electoralVotes;
                row++;
            }
        }
        return stateInfo;
    }

    public static String extractTotalArea(String input) {
        Pattern pattern = Pattern.compile("\\b(\\d{1,3},?)+\\b");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group().replaceAll(",", "");
        } else {
            return null;
        }
    }

    public static Map<String, List<String>> getBorderInfo() throws IOException {
        String borderURL = "https://state.1keydata.com/bordering-states-list.php";
        Document doc = connectToWebsite(borderURL);
        Map<String, List<String>> borderInfo = new HashMap<>();

        Element firstTable = doc.select("div#ncol1 table.content4").first();

        // Select all rows in the first table except the first one (header row)
        Elements rows = firstTable.select("tr:not(:first-child)");
        for (Element row : rows) {
            String state = row.select("td:eq(0) a").text();
            String borderingStates = row.select("td:eq(1)").text();

            String[] borderingStatesArray = borderingStates.split(",");
            List<String> borderingStatesList = new ArrayList<>();
            for (String borderingState : borderingStatesArray) {
                borderingStatesList.add(borderingState.trim());
            }

            borderInfo.put(state, borderingStatesList);
        }

        return borderInfo;
    }

    public static List<String> findShortestPath(String startState, String endState) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(startState);
        visited.add(startState);
        parentMap.put(startState, null);

        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            if (currentState.equals(endState)) {
                break;
            }
            List<String> neighbors = borderInfo.get(currentState);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                        parentMap.put(neighbor, currentState);
                    }
                }
            }
        }

        // Reconstruct path
        List<String> shortestPath = new ArrayList<>();
        if (!visited.contains(endState)) {
            return shortestPath; // No path found
        }
        String currentState = endState;
        while (currentState != null) {
            shortestPath.add(currentState);
            currentState = parentMap.get(currentState);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

}
