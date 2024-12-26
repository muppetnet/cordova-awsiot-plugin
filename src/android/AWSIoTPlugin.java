package cordova-awsiot-plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AWSIoTPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("connect")) {
                JSONObject options = args.getJSONObject(0);
                String endpoint = options.getString("endpoint");
                String certPath = options.getString("certPath");
                String keyPath = options.getString("keyPath");

                MqttConnectionBuilder builder = AwsIotMqttConnectionBuilder.newMtlsBuilderFromPath(certPath, keyPath)
                    .withEndpoint(endpoint);

                this.connection = builder.build();
                this.connection.connect().get();

                callbackContext.success("Connected");
                return true;
            } else if (action.equals("publish")) {
                String message = args.getString(0);
                this.connection.publish(new PublishPacket.Builder()
                        .topic("test/topic")
                        .payload(message.getBytes())
                        .build());
                callbackContext.success("Message Published");
                return true;
            } else if (action.equals("subscribe")) {
                String topic = args.getString(0);
                this.connection.subscribe(topic, QualityOfService.AT_LEAST_ONCE, (packet) -> {
                    callbackContext.success(new String(packet.getPayload()));
                });
                return true;
            }
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
        }
        return false;
    }
}
