/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.mfaas.client.api;

import com.ca.mfaas.client.configuration.ApplicationConfiguration;
import com.ca.mfaas.client.configuration.SpringComponentsConfiguration;
import com.ca.mfaas.client.model.Pet;
import com.ca.mfaas.client.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {PetController.class}, secure = false)
@Import(value = {SpringComponentsConfiguration.class, ApplicationConfiguration.class})
public class PetControllerPostPetTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addPetWithValidObject() throws Exception {
        String name = "Linux";
        int id = 1;
        Pet pet = new Pet(null, name);
        String payload = mapper.writeValueAsString(pet);
        when(petService.save(any(Pet.class))).thenReturn(new Pet((long) id, name));

        this.mockMvc.perform(
            post("/api/v1/pets")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(id)))
            .andExpect(jsonPath("$.name", is(name)));

        verify(petService, times(1)).save(any(Pet.class));
    }

}
