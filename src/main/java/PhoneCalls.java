import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.CallCreator;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Play;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;

import java.net.URI;

import static spark.Spark.get;
import static spark.Spark.post;

public class PhoneCalls {


    // find your Account SID & Auth Token in Twilio Console: twilio.com/console
    public static final String ACCOUNT_SID = "ACf38cb8f2e0b42edd973d52947563ec00"; // your Account SID found in the Twilio Console
    public static final String AUTH_TOKEN = "c5dbb80ac51fef436c22de1343575307"; // your auth token also found in the Twilio Console

    // use the +12025551234 format for the value in the following constant
    public static final String TWILIO_NUMBER = "+12052932581"; // Twilio phone number for dialing outbound phone calls

    public static final String NGROK_BASE_URL = "https://aa910607.ngrok.io"; // paste your ngrok Forwarding URL such as https://0e64e563.ngrok.io


    // lets us know our app is up and running
    public static void main(String[] args) {
        get("/", (req, res) -> "Spark app is running!");

        TwilioRestClient client = new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_TOKEN).build();


        // twiml endpoint
        post("/twiml", (request, response) -> {
            // generate the TwiML response to tell Twilio what to do
            Say sayHello = new Say.Builder("Hello from Twilio, Java 8 and Spark!").build();
            Play playSong = new Play.Builder("https://api.twilio.com/cowbell.mp3").build();
            VoiceResponse voiceResponse = new VoiceResponse.Builder().say(sayHello).play(playSong).build();
            return voiceResponse.toXml();
        });


        // this endpoint handles dialing outbound phone calls with the TwilioRestClient object
        get("/dial-phone/:number", (request, response) -> {
            String phoneNumber = request.params(":number");
            /* as long as the phone number is not blank or null, we'll attempt to dial it, but
               you can add more exception handling here */
            if (!phoneNumber.isEmpty()) {
                PhoneNumber to = new PhoneNumber(phoneNumber);
                PhoneNumber from = new PhoneNumber(TWILIO_NUMBER);
                URI uri = URI.create(NGROK_BASE_URL + "/twiml");

                // Make the call using the TwilioRestClient we instantiated
                Call call = new CallCreator(to, from, uri).create(client);
                return "Dialing " + phoneNumber + " from your Twilio phone number...";
            } else {
                return "Hey, you need to enter a valid phone number in the URL!";
            }
        });

        /*

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(TWILIO_NUMBER),
                new PhoneNumber(""),
                "This is the ship that made the Kessel Run in fourteen parsecs?").create();

        System.out.println(message.getSid());


         */
    }


}
