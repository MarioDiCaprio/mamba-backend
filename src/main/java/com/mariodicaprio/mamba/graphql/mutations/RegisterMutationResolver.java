package com.mariodicaprio.mamba.graphql.mutations;


import com.mariodicaprio.mamba.requests.RegisterRequest;
import com.mariodicaprio.mamba.responses.RegisterResponse;
import com.mariodicaprio.mamba.services.RegisterService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@SuppressWarnings("unused")
public class RegisterMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private RegisterService registerService;

    /////////////////////////////////////////////////////////////////////////////////////

    public RegisterResponse register(RegisterRequest request) {
        return registerService.register(request);
    }

}
