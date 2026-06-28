import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactServiceTest {
    private ContactService contactService;
    private Contact contact;

    @BeforeEach
    void setUp() {
        contactService = new ContactService();
        contact = new Contact("12345", "John", "Smith", "1234567890", "123 Main Street");
    }

    @Test
    void testAddContactSuccessfully() {
        contactService.addContact(contact);

        assertEquals(1, contactService.getContactCount());
        assertEquals(contact, contactService.getContact("12345"));
    }

    @Test
    void testCannotAddNullContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(null);
        });
    }

    @Test
    void testCannotAddDuplicateContactId() {
        Contact duplicateContact = new Contact("12345", "Jane", "Jones", "0987654321", "456 Oak Ave");

        contactService.addContact(contact);

        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(duplicateContact);
        });
    }

    @Test
    void testDeleteContactSuccessfully() {
        contactService.addContact(contact);
        contactService.deleteContact("12345");

        assertEquals(0, contactService.getContactCount());
    }

    @Test
    void testCannotDeleteNonexistentContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact("99999");
        });
    }

    @Test
    void testUpdateFirstNameSuccessfully() {
        contactService.addContact(contact);
        contactService.updateFirstName("12345", "Jane");

        assertEquals("Jane", contactService.getContact("12345").getFirstName());
    }

    @Test
    void testUpdateLastNameSuccessfully() {
        contactService.addContact(contact);
        contactService.updateLastName("12345", "Jones");

        assertEquals("Jones", contactService.getContact("12345").getLastName());
    }

    @Test
    void testUpdatePhoneSuccessfully() {
        contactService.addContact(contact);
        contactService.updatePhone("12345", "0987654321");

        assertEquals("0987654321", contactService.getContact("12345").getPhone());
    }

    @Test
    void testUpdateAddressSuccessfully() {
        contactService.addContact(contact);
        contactService.updateAddress("12345", "456 Oak Ave");

        assertEquals("456 Oak Ave", contactService.getContact("12345").getAddress());
    }

    @Test
    void testCannotUpdateNonexistentContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName("99999", "Jane");
        });
    }

    @Test
    void testCannotUpdateWithInvalidValues() {
        contactService.addContact(contact);

        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateFirstName("12345", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateLastName("12345", "Smithsonian");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updatePhone("12345", "12345");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateAddress("12345", "1234567890123456789012345678901");
        });
    }
}
