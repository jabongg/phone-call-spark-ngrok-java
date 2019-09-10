import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import java.util.List;

public class AmazonSNSMulti {
    public static void main(String[] args) {
        // Your Credentials
        String ACCESS_KEY = "YOUR_ACCESS_KEY";
        String SECRET_KEY = "YOUR_SECRET_KEY";
        String topicName = "myTopic";
        String message = "YOUR MESSAGE";
        // Populate the list of phoneNumbers
        List<String> phoneNumbers = null;  // Ex: +919384374XX
        AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
        // Create SMS Topic
        String topicArn = createSNSTopic(snsClient, topicName);
        // Subcribe Phone Numbers to Topic
        subscribeToTopic(snsClient, topicArn, "sms", phoneNumbers);
        // Publish Message to Topic
        sendSMSMessageToTopic(snsClient, topicArn, message);
    }
    public static String createSNSTopic(AmazonSNSClient snsClient,
                                        String topicName) {
        CreateTopicRequest createTopic = new
                CreateTopicRequest(topicName);
        CreateTopicResult result =
                snsClient.createTopic(createTopic);
        return result.getTopicArn();
    }
    public static void subscribeToTopic(AmazonSNSClient snsClient, String topicArn,
                                        String protocol, List<String> phoneNumbers) {
        for (String phoneNumber : phoneNumbers) {
            SubscribeRequest subscribe = new SubscribeRequest(topicArn, protocol, phoneNumber);
            snsClient.subscribe(subscribe);
        }
    }
    public static String sendSMSMessageToTopic(AmazonSNSClient snsClient, String topicArn, String message) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withTopicArn(topicArn)
                .withMessage(message));
        return result.getMessageId();
    }
}