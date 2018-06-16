package secondaryindex;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

public class VocabulariesGlobalIndex {

    public static void main(String... arg) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:5555", "us-west-2"))
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        // Retrieve the reference to an existing Amazon DynamoDB table.
        Table table = dynamoDB.getTable("Vocabularies");

        // Create a new Global Secondary Index.
        Index index = table.createGSI(
                new CreateGlobalSecondaryIndexAction()
                        .withIndexName("deckId-index")
                        .withKeySchema(
                                new KeySchemaElement("deckId", KeyType.HASH))
                        .withProvisionedThroughput(
                                new ProvisionedThroughput(25L, 25L))
                        .withProjection(
                                new Projection()
                                        .withProjectionType(ProjectionType.ALL)),
                new AttributeDefinition("deckId",
                        ScalarAttributeType.S));

// Wait until the index is active.
        try {
            index.waitForActive();
        } catch (Exception e) {
            System.out.println("Something went wrong" + e);
        }
    }
}
