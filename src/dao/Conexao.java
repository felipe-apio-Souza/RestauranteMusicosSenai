/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import application.Artista;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author felipe_apio-souza
 */
public class Conexao {
    private static Connection connection;
   
    public static Connection getConnection(){
        if(connection == null){
            try{
                //Class.forName("com.mysql.jdbc.Driver"); //para mysql
                Class.forName("org.postgresql.Driver");//para postgresql
                String host = "localhost";
                String port = "5432";
                String database = "senaiRestaurantesMusicos";
                String user = "postgres";
                String pass = "postgres";//digitar a senha do seu banco
                //String url = "jdbc:mysql://"+host+":"+port+"/"+database; //para mysql
                String url = "jdbc:postgresql://"+host+":"+port+"/"+database;//para postgresql
                connection = DriverManager.getConnection(url, user, pass);                
               
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static void close(){
        if (connection == null){
            throw new RuntimeException("Nenhuma conexão aberta!");
        }
        else{
            try{
                connection.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
 
    
    public static void salvarArtista (Artista artista){
        try{
            Connection con = Conexao.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO musicos (cpf,nome, numero, email, categoria, estado, cidade, bairro, numero_casa, cep, senha) values(?, ?, ? , ? , ? , ? , ?, ? , ? , ? , ?)");
            ps.setString(1, artista.getCpf());
            ps.setString(2, artista.getNome());
            ps.setString(3, artista.getNumero());        
            ps.setString(4, artista.getEmail());
            ps.setString(5, artista.getCategoria());
            ps.setString(6, artista.getEstado());
            ps.setString(7, artista.getCidade());
            ps.setString(8, artista.getBairro());
            ps.setString(9, artista.getNumero_casa());
            ps.setString(10, artista.getCep()); 
            ps.setString(11, artista.getSenha());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deletaArtista(String cpf){
        try{
            Connection con = Conexao.getConnection();
            PreparedStatement ps = con.prepareStatement("Delete FROM artista WHERE cpf = ?");
            ps.setString(1, cpf);
            ps.executeUpdate();


            }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void atualizaArtista (Artista artista){
        deletaArtista(artista.getCpf());
        salvarArtista(artista);
    }

}
