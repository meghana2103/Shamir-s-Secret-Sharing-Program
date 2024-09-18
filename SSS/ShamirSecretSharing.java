import java.util.*;

public class ShamirSecretSharing {

    // Function to decode a value given its base
    public static long decodeValue(String value, int base) {
        return Long.parseLong(value, base);
    }

    // Function to perform Lagrange interpolation to find the constant term (c)
    public static double lagrangeInterpolation(List<long[]> points) {
        double c = 0.0;

        // Iterate over all points
        for (int i = 0; i < points.size(); i++) {
            long xi = points.get(i)[0];
            long yi = points.get(i)[1];

            // Compute the Lagrange basis polynomial L_i(0)
            double li = 1.0;
            for (int j = 0; j < points.size(); j++) {
                if (i != j) {
                    long xj = points.get(j)[0];
                    li *= (0.0 - xj) / (xi - xj);
                }
            }

            // Add to the constant term
            c += yi * li;
        }

        return c;
    }

    // Main function to parse input and find the secret 'c'
    public static double findConstantTerm(Map<String, Object> data) {
        Map<String, Integer> keys = (Map<String, Integer>) data.get("keys");

        int n = keys.get("n");
        int k = keys.get("k");

        List<long[]> points = new ArrayList<>();

        // Collect points (x, y) after decoding y values
        for (String key : data.keySet()) {
            if (key.equals("keys")) {
                continue; // Skip the "keys" part
            }

            long x = Long.parseLong(key); // The x-value is the key
            Map<String, String> pointData = (Map<String, String>) data.get(key);
            int base = Integer.parseInt(pointData.get("base"));
            String encodedY = pointData.get("value");

            long y = decodeValue(encodedY, base); // Decode y-value based on base
            points.add(new long[]{x, y});
        }

        // Use Lagrange interpolation on the first k points
        return lagrangeInterpolation(points.subList(0, k));
    }

    public static void main(String[] args) {
        // Test Case 1
        Map<String, Object> inputData1 = new HashMap<>();
        Map<String, Integer> keys1 = new HashMap<>();
        keys1.put("n", 4);
        keys1.put("k", 3);
        inputData1.put("keys", keys1);

        Map<String, String> point1 = new HashMap<>();
        point1.put("base", "10");
        point1.put("value", "4");
        inputData1.put("1", point1);

        Map<String, String> point2 = new HashMap<>();
        point2.put("base", "2");
        point2.put("value", "111");
        inputData1.put("2", point2);

        Map<String, String> point3 = new HashMap<>();
        point3.put("base", "10");
        point3.put("value", "12");
        inputData1.put("3", point3);

        Map<String, String> point6 = new HashMap<>();
        point6.put("base", "4");
        point6.put("value", "213");
        inputData1.put("6", point6);

        // Test Case 2
        Map<String, Object> inputData2 = new HashMap<>();
        Map<String, Integer> keys2 = new HashMap<>();
        keys2.put("n", 9);
        keys2.put("k", 6);
        inputData2.put("keys", keys2);

        Map<String, String> point1_case2 = new HashMap<>();
        point1_case2.put("base", "10");
        point1_case2.put("value", "28735619723837");
        inputData2.put("1", point1_case2);

        Map<String, String> point2_case2 = new HashMap<>();
        point2_case2.put("base", "16");
        point2_case2.put("value", "1A228867F0CA");
        inputData2.put("2", point2_case2);

        Map<String, String> point3_case2 = new HashMap<>();
        point3_case2.put("base", "12");
        point3_case2.put("value", "32811A4AA0B7B");
        inputData2.put("3", point3_case2);

        Map<String, String> point4_case2 = new HashMap<>();
        point4_case2.put("base", "11");
        point4_case2.put("value", "917978721331A");
        inputData2.put("4", point4_case2);

        Map<String, String> point5_case2 = new HashMap<>();
        point5_case2.put("base", "16");
        point5_case2.put("value", "1A22886782E1");
        inputData2.put("5", point5_case2);

        Map<String, String> point6_case2 = new HashMap<>();
        point6_case2.put("base", "10");
        point6_case2.put("value", "28735619654702");
        inputData2.put("6", point6_case2);

        Map<String, String> point7_case2 = new HashMap<>();
        point7_case2.put("base", "14");
        point7_case2.put("value", "71AB5070CC4B");
        inputData2.put("7", point7_case2);

        Map<String, String> point8_case2 = new HashMap<>();
        point8_case2.put("base", "9");
        point8_case2.put("value", "122662581541670");
        inputData2.put("8", point8_case2);

        Map<String, String> point9_case2 = new HashMap<>();
        point9_case2.put("base", "8");
        point9_case2.put("value", "642121030037605");
        inputData2.put("9", point9_case2);

        // Find and print the constant term 'c' for both test cases
        double constantTerm1 = findConstantTerm(inputData1);
        System.out.println("The constant term c for Test Case 1 is: " + constantTerm1);

        double constantTerm2 = findConstantTerm(inputData2);
        System.out.println("The constant term c for Test Case 2 is: " + constantTerm2);
    }
}