package practica.dao;

import java.util.List;

import practica.javabean.Cliente;

public interface ClienteDao {
	
	Cliente findBy(int idCliente);
	List<Cliente> findAll();
	int insert (Cliente cliente);
	int delete(int idCliente);
	int updateOne(Cliente cliente);

}
