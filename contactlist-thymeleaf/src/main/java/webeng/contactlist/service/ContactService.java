package webeng.contactlist.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import webeng.contactlist.model.Contact;
import webeng.contactlist.model.ContactListEntry;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class ContactService {

    private static final String JSON_FILE = "contacts.json";

    private final Map<Integer, Contact> contacts;


    public ContactService(ObjectMapper mapper) throws IOException {
        var contactsList = mapper.readValue(ContactService.class.getResource(JSON_FILE),
                new TypeReference<List<Contact>>() {});
        contacts = contactsList.stream()
                .collect(toMap(Contact::getId, identity()));
    }

    public List<ContactListEntry> getContactList(String search) {

        return contacts.values().stream()
                .sorted(comparing(Contact::getId))
                .filter(c -> search  == null || //zuerst abchecken ob null wenn ja wird alles zurückgegeben.
                        c.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        c.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        c.getEmail().contains(search))
                .map(c -> new ContactListEntry(c.getId(), c.getFirstName() + " " + c.getLastName()))
                .collect(toList());
    }

    public int countAllMails(){
        int count = 0;

            for (int i = 0; i < contacts.size(); i++) {
                if(contacts.get(i) != null){
                    for(int r = 0; r<=contacts.get(i).getEmail().size(); r++) {
                        contacts.get(i).getEmail();
                        count++;
                    }
                }

            }
        return count;
    }

    public int countAllPhoneNumbers(){
        int count = 0;

        for(int i=0; i<contacts.size(); i++){
            if(contacts.get(i) != null){
                for(int r = 0; r<=contacts.get(i).getPhone().size(); r++) {
                    contacts.get(i).getPhone();
                    count++;
                }
            }

        }
        return count;
    }



    public Optional<Contact> findContact(int id) {
        return Optional.ofNullable(contacts.get(id));
    }
}
