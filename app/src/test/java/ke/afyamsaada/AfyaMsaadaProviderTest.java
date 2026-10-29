package ke.afyamsaada;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ke.afyamsaada.model.Facility;
import ke.afyamsaada.model.Question;
import ke.afyamsaada.model.Resource;

public class AfyaMsaadaProviderTest {

    @Test
    public void testFacilityModelProperties() {
        Facility facility = new Facility(1, "Test Rehab Center", "Nairobi", "Westlands", "0700123456", "Inpatient, Outpatient", "Private", true);

        assertEquals(1, facility.getId());
        assertEquals("Test Rehab Center", facility.getName());
        assertEquals("Nairobi", facility.getCounty());
        assertEquals("Westlands", facility.getLocation());
        assertEquals("0700123456", facility.getContactPhone());
        assertEquals("Inpatient, Outpatient", facility.getServices());
        assertEquals("Private", facility.getType());
        assertTrue(facility.isVerified());
    }

    @Test
    public void testResourceModelProperties() {
        Resource resource = new Resource(1, "Alcohol", "Alcohol Misuse Guidance", "Summary text", "Full content text", "Health risks text", "Support tips text");

        assertEquals(1, resource.getId());
        assertEquals("Alcohol", resource.getCategory());
        assertEquals("Alcohol Misuse Guidance", resource.getTitle());
        assertEquals("Summary text", resource.getSummary());
        assertEquals("Full content text", resource.getFullContent());
        assertEquals("Health risks text", resource.getHealthRisks());
        assertEquals("Support tips text", resource.getSupportTips());
    }

    @Test
    public void testQuestionScoringLogic() {
        Question q = new Question(1, "Frequency of use?", "Alcohol", new String[]{"Never", "Weekly", "Daily"}, new int[]{0, 5, 10});

        assertEquals(0, q.getSelectedScore());

        q.setSelectedOptionIndex(1);
        assertEquals(5, q.getSelectedScore());

        q.setSelectedOptionIndex(2);
        assertEquals(10, q.getSelectedScore());
    }
}
