package cn.sakuraxiafan.retailer.dao.impl;

import cn.sakuraxiafan.retailer.dao.BaseDao;
import cn.sakuraxiafan.util.DBMapper;
import cn.sakuraxiafan.util.DBUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BaseDaoImpl<T> implements BaseDao<T> {

    private final DBMapper<T> mapper;
    private final String targetName;/*类名，小写*/
    private final Field[] declaredFields;
    public BaseDaoImpl(Class<T> target) {
        mapper = new DBMapper<>(target);
        targetName=target.getSimpleName().toLowerCase();
        declaredFields = target.getDeclaredFields();
    }

    public int insert(T t){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        StringBuilder builder = new StringBuilder("insert into ").append(targetName.toLowerCase()).append(" (");
        for (int i = 0; i < declaredFields.length; i++) {
            builder.append(mapper.map(declaredFields[i].getName()));
            if (i!=declaredFields.length-1){
                builder.append(",");
            }
        }
        builder.append(") values (");
        for (int i = 0; i < declaredFields.length; i++) {
            builder.append("?");
            if (i!=declaredFields.length-1){
                builder.append(",");
            }
        }
        builder.append(")");
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(builder.toString());
            for (int i = 0; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                preparedStatement.setObject(i+1,declaredFields[i].get(t));
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
//            e.printStackTrace();
            return  -1;
        } finally {
            DBUtil.closeAll(null,preparedStatement,null);
        }
    }
    public int simpleDelete(String fieldName, Object value){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        StringBuilder builder = new StringBuilder("delete from ");
        builder.append(targetName).append(" where ");
        if (Arrays.stream(declaredFields).noneMatch(item -> item.getName().equals(fieldName)&&( value.getClass().equals(item.getType())))){
            throw new RuntimeException("删除参数指定错误");
        }
        builder.append(mapper.map(fieldName)).append(" = ").append("?");
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(builder.toString());
            preparedStatement.setObject(1,value);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return  -1;
        }finally {
            DBUtil.closeAll(null,preparedStatement,null);
        }
    }


    public int simpleUpdate(String fieldName, Object value, T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        StringBuilder builder = new StringBuilder("update ").append(targetName).append(" set ");
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            builder.append(mapper.map(declaredFields[i].getName())).append(" = ?");
            if (i!=declaredFields.length-1){
                builder.append(", ");
            }
        }
        builder.append("where ").append(mapper.map(fieldName)).append(" = ?");
        System.out.println(builder);
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(builder.toString());
            for (int i = 0; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                preparedStatement.setObject(i+1,declaredFields[i].get(t));
                if (i==declaredFields.length-1){
                    preparedStatement.setObject(i+2,value);
                }
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return  -1;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeAll(null,preparedStatement,null);
        }
    }


    public List<T> simpleQuery(String fieldName, Object value){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> arrayList= new ArrayList<>();
        if (Arrays.stream(declaredFields).noneMatch(item -> item.getName().equals(fieldName)&&( value.getClass().equals(item.getType())))){
            throw new IllegalArgumentException("查询参数指定错误");
        }
        StringBuilder builder = new StringBuilder("select * from ").append(targetName).append(" where ").append(mapper.map(fieldName)).append(" = ?");
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(builder.toString());
            preparedStatement.setObject(1,value);
            resultSet = preparedStatement.executeQuery();
            packageResult(resultSet,arrayList);
            return arrayList;
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            return arrayList;
        } finally {
            DBUtil.closeAll(null,preparedStatement,resultSet);
        }
    }


    public List<T> multiQuery(String[] fieldName, Object[] value){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> arrayList= new ArrayList<>();
        StringBuilder builder = new StringBuilder("select * from ").append(targetName).append(" where ");
        fillPredicate(fieldName,builder);
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(builder.toString());
            for (int i = 0; i < fieldName.length; i++) {
                preparedStatement.setObject(i+1,value[i]);
            }
            resultSet = preparedStatement.executeQuery();
            packageResult(resultSet,arrayList);
            return arrayList;
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            return arrayList;
        }finally {
            DBUtil.closeAll(null,preparedStatement,resultSet);
        }
    }


    public List<T> queryAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> arrayList= new ArrayList<>();
        StringBuilder builder = new StringBuilder("select * from ").append(targetName);
        try {
            connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(builder.toString());
            resultSet = preparedStatement.executeQuery();
            packageResult(resultSet,arrayList);
            return arrayList;
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            return arrayList;
        } finally {
            DBUtil.closeAll(null,preparedStatement,resultSet);
        }
    }
    private void packageResult(ResultSet resultSet,List<T> arrayList) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        while (resultSet.next()){
            T t = mapper.getTarget().getConstructor().newInstance();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                declaredField.set(t, resultSet.getObject(mapper.map(declaredField.getName())));
            }
            arrayList.add(t);
        }
    }

    private void fillPredicate(String[] fieldName,StringBuilder builder){
        for (int i = 0; i < fieldName.length; i++) {
            boolean contains = false;
            for (Field declaredField : declaredFields) {
                if (declaredField.getName().equals(fieldName[i])) {
                    contains = true;
                    break;
                }
            }
            if (!contains){
                throw new IllegalArgumentException("查询参数"+fieldName[i]+"不存在!");
            }
            builder.append(mapper.map(fieldName[i])).append(" = ?");
            if (i!=fieldName.length-1){
                builder.append(" and ");
            }
        }
    }
//    public int multiDelete(){
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        StringBuilder builder = new StringBuilder("delete from ");
//        builder.append(targetName).append(" where ").append(fieldName).append(" = ");
//        for (Field declaredField : declaredFields) {
//            Class<?> type = declaredField.getType();
//            if (type.isInstance(value) && fieldName.equals(declaredField.getName())) {
//                builder.append(value);
//                break;
//            }
//        }
//        System.out.println(builder);
//        try {
//            connection = DBUtil.getConnection();
//            preparedStatement = connection.prepareStatement(builder.toString());
//            preparedStatement.setObject(1,value);
//            return preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return  -1;
//        }finally {
//            DBUtil.closeAll(null,preparedStatement,null);
//        }
//    }
}
