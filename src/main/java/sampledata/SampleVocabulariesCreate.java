package sampledata;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class SampleVocabulariesCreate {
    public static void main(String[] args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:5555", "us-west-2"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("Vocabularies");

        int id = 1;
        String name = "Francouzština pro samouky";
        Long userId = 1L;
        Long numberOfNew = 1L;

        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("id", id)
                            .withString("name", name)
                            .withLong("userId", userId)
                            .withLong("numborOfNew", numberOfNew));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        } catch (Exception e) {
            System.err.println("Unable to add item: " + id + " " + name);
            System.err.println(e.getMessage());
        }
    }
}
