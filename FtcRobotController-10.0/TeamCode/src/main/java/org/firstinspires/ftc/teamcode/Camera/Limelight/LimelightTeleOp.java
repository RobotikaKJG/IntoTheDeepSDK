package org.firstinspires.ftc.teamcode.Camera.Limelight;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@TeleOp(name = "Limelight TeleOp", group = "TeleOp")
public class LimelightTeleOp extends OpMode {
    private static final String LIMELIGHT_IP = "http://172.29.0.1:5801"; // Replace with your Limelight IP address

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        try {
            // Retrieve Limelight data via HTTP
            String data = getLimelightData();
            telemetry.addData("Limelight Data", data);

            // Parse specific data if needed (e.g., tx, ty, ta)
            double tx = getTargetOffsetX(data); // Extract horizontal offset (tx) if available
            telemetry.addData("Target X Offset", tx);
            // Add additional parsing here for ty, ta, etc., if required

            // Control logic based on tx (or other data) could go here
            if (tx != 0) {
                // Example control logic (e.g., adjust robot orientation based on tx)
                // Add motor or servo control code here
            }

        } catch (Exception e) {
            telemetry.addData("Error", e.toString());
        }

        telemetry.update();
    }

    private String getLimelightData() throws Exception {
        URL url = new URL(LIMELIGHT_IP + "/api/v1/targetData"); // Adjust endpoint as needed
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }

    private double getTargetOffsetX(String data) {
        // Extract the tx (horizontal offset) value from the data string
        // Assuming data is in JSON format and has a "tx" field; this parsing will vary based on Limelight's data structure
        double tx = 0.0;
        if (data.contains("\"tx\":")) {
            int startIndex = data.indexOf("\"tx\":") + 5;
            int endIndex = data.indexOf(",", startIndex);
            tx = Double.parseDouble(data.substring(startIndex, endIndex).trim());
        }
        return tx;
    }
}
