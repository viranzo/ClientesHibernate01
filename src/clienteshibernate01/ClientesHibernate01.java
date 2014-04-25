package clienteshibernate01;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author Vicente
 */
public class ClientesHibernate01 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SessionFactory sessionFactory;
        
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        
        Session session=sessionFactory.openSession();
        
        session.beginTransaction(); // iniciar una transaccion
        
        Cliente cliente1 = new 
                Cliente(1234567, "Vicente", "Iranzo", "vte", "Vic$&1");
        
        // Guardar el cliente en la base de datos
        session.save(cliente1);
        session.getTransaction().commit(); // terminamos la transaccion
        
           // Obtener el cliente de la base de datos
        Cliente cliente2=(Cliente)session.get(Cliente.class,1234567);
        
        System.out.printf("%-8s %-15s %-15s %-15s \n",
                "DNI",
                "Nombre",
                "Primer Apellido",
                "Nick");
        System.out.printf("%8d %-15s %-15s %-15s \n",
                cliente2.getDni(),
                cliente2.getNombre(),
                cliente2.getApe1(),
                cliente2.getNick()
                );
        // modificamos el nic del cliente
        cliente2.setNick("viranzo");
        
        // iniciar una nueva transaccion para modificar el cliente
        session.beginTransaction();
        session.update(cliente2);
        session.getTransaction().commit();// terminamos la transaccion
        
        // iniciar una nueva transaccion para borrar el cliente
        session.beginTransaction();
        session.delete(cliente2);
        session.getTransaction().commit(); 
        
         // cerrar la sesion
        session.close();
        sessionFactory.close();
    }
}
