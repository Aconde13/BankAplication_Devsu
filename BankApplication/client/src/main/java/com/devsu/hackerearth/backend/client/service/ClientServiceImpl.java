package com.devsu.hackerearth.backend.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

import com.devsu.hackerearth.backend.client.model.Client;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		List<Client> clients = clientRepository.findAll();
		return clients.stream().map(client -> {
			return new ClientDto(client.getId(), client.getDni(), client.getName(),
			client.getPassword(), client.getGender(), client.getAge(),
			client.getAddress(), client.getPhone(), client.isActive());
		}).collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		Optional<Client> clientOp = clientRepository.findById(id);
		if(clientOp.isPresent()){ 
			Client client = clientOp.get();
			return new ClientDto(client.getId(), client.getDni(), client.getName(),
			client.getPassword(), client.getGender(), client.getAge(),
			client.getAddress(), client.getPhone(), client.isActive());
		}
		return null;
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = new Client();
		client.setDni(clientDto.getDni());
		client.setName(clientDto.getName());
		client.setPassword(clientDto.getPassword());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setActive(clientDto.isActive());
		client = clientRepository.save(client);
		clientDto.setId(client.getId());
		return clientDto;
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		Optional<Client> clientOp = clientRepository.findById(clientDto.getId());
		if(clientOp.isPresent()){ 
			Client client = clientOp.get();

			client.setDni(clientDto.getDni());
			client.setName(clientDto.getName());
			client.setPassword(clientDto.getPassword());
			client.setGender(clientDto.getGender());
			client.setAge(clientDto.getAge());
			client.setAddress(clientDto.getAddress());
			client.setPhone(clientDto.getPhone());
			client.setActive(clientDto.isActive());
			client = clientRepository.save(client);

			return new ClientDto(client.getId(), client.getDni(), client.getName(),
			client.getPassword(), client.getGender(), client.getAge(),
			client.getAddress(), client.getPhone(), client.isActive());
		}
		return null;
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Partial update account
		Optional<Client> clientOp = clientRepository.findById(id);
		if(clientOp.isPresent()){ 
			Client client = clientOp.get();

			client.setActive(partialClientDto.isActive());
			client = clientRepository.save(client);

			return new ClientDto(client.getId(), client.getDni(), client.getName(),
			client.getPassword(), client.getGender(), client.getAge(),
			client.getAddress(), client.getPhone(), client.isActive());
		}
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// Delete client
		clientRepository.deleteById(id);
	}
}
