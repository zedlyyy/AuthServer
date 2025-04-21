package com.authserver.repositories;
import org.springframework.data.repository.CrudRepository;

import com.authserver.objects.AuthCode;

public interface CrudAuthCodes extends CrudRepository<AuthCode, String> {

}
