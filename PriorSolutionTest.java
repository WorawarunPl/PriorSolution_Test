package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PriorSolutionTest {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("RawData.txt"));
            Gson gson = new Gson();
            Map<String, List<Map<String, Object>>> data = gson.fromJson(
                    reader,
                    new TypeToken<Map<String, List<Map<String, Object>>>>() {
                    }.getType());
            reader.close();

            if (data.containsKey("nodes") && data.containsKey("edges")) {

                List<Map<String, Object>> nodes = data.get("nodes");
                List<Map<String, Object>> edges = data.get("edges");

                Map<String, Map<String, Object>> nodeById = new HashMap<>();
                for (Map<String, Object> node : nodes) {
                    nodeById.put((String) node.get("id"), node);
                }

                List<String> nodeTypes = new ArrayList<>();
                List<String> addressIn = new ArrayList<>();
                List<String> addressOut = new ArrayList<>();

                // First, process the input node(s)
                for (Map<String, Object> node : nodes) {
                    if ("input".equals(node.get("type"))) {
                        nodeTypes.add("'input'");
                        addressIn.add("''");
                    }
                }

                for (Map<String, Object> edge : edges) {
                    String target = (String) edge.get("target");
                    addressOut.add(String.format("'%s'", target));

                    if (nodeById.containsKey(target)) {
                        nodeTypes.add(String.format("'%s'", nodeById.get(target).get("type")));
                        for (Map<String, Object> e : edges) {
                            if (target.equals(e.get("source"))) {
                                addressIn.add(String.format("'%s'", e.get("source")));
                            }
                        }
                    }
                }

                for (Map<String, Object> node : nodes) {
                    boolean isSource = false;
                    for (Map<String, Object> edge : edges) {
                        if (node.get("id").equals(edge.get("source"))) {
                            isSource = true;
                            break;
                        }
                    }
                    if (!isSource) {
                        addressOut.add("''");
                    }
                }

                System.out.println("\nNode = " + nodeTypes);
                System.out.println("\naddressIn = " + addressIn);
                System.out.println("\naddressOut = " + addressOut);
            } else {
                System.err.println("Error: ไม่พบข้อมูล");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
