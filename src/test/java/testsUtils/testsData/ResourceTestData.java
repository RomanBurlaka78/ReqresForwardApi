package testsUtils.testsData;

import org.testng.annotations.DataProvider;

public class ResourceTestData {

    public static final int VALID_RESOURCE_ID= 2;
    public static final int NOT_VALID_RESOURCE_ID= 23;

    @DataProvider
    public static Object[] dataResourceId() {
        return new Object[][]{
                {33},
                {233},
                {123},
                {45}
        };
    }
}
