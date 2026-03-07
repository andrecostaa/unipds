package br.com.alac.resources;

import br.com.alac.entities.Person;
import io.micrometer.core.annotation.Counted;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    @Counted(value= "counted.getPessoa")
    public List<Person> getPeople() {
        return Person.listAll();
    }

    @GET
    @Path("findByYearOfBirthday")
    public List<Person> findByYearOfBirthday(@QueryParam("yearOfBirthday") int yearOfBirthday) {
        return Person.findByYearOfBirthday(yearOfBirthday);
    }

    @POST
    @Transactional
    public Person createPerson(Person person) {
        person.id = null;
        person.persist();

        return person;
    }

    @PUT
    @Transactional
    public Person updatePerson(Person person) {
        Person p = Person.findById(person.id);
        p.name = person.name;
        p.yearOfBirthday = person.yearOfBirthday;
        p.persist();

        return p;
    }

    @DELETE
    @Transactional
    public void deletePerson(int id) {
       Person.deleteById(id);
    }
}
