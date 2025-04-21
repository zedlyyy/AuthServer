package com.authserver.repositories;
import org.springframework.data.repository.CrudRepository;
import com.authserver.objects.User;

public interface CrudRepositoryUsers extends CrudRepository<User, String>{

}
