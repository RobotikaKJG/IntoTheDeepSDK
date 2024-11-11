package org.firstinspires.ftc.teamcode.Camera.Limelight;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LimelightHTTPIntegration extends OpMode {
    private static final String LIMELIGHT_IP = "http://172.29.0.1:5801"; // Replace with your Limelight IP address

    @Override
    public void init() {
        // Any initialization logic here
    }

    @Override
    public void loop() {
        try {
            // Get data from Limelight via HTTP
            String data = getLimelightData();
            telemetry.addData("Limelight Data", data);
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

        // Close connections
        in.close();
        connection.disconnect();

        return content.toString();
    }
}
