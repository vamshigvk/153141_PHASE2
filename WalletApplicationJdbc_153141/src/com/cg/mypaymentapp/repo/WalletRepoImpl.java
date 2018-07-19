package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.util.DBUtil;

public class WalletRepoImpl implements WalletRepo {
	public boolean save(Customer customer) {
		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement pstm1 = con.prepareStatement("insert into customer values(?,?,?)");
			pstm1.setString(1, customer.getName());
			pstm1.setString(2, customer.getMobileNo());
			pstm1.setBigDecimal(3, customer.getWallet().getBalance());
			pstm1.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Customer findOne(String mobileNo) {
		Customer cus = null;
		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer WHERE customer.mobileno = ?");
			pstm.setString(1, mobileNo);
			ResultSet rs = pstm.executeQuery();
			if (rs.next() != false) {
				cus = new Customer();
				cus.setName(rs.getString(1));
				cus.setMobileNo(rs.getString(2));
				Wallet wallet = new Wallet(rs.getBigDecimal(3));
				cus.setWallet(wallet);
			} else {
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}
		return cus;
	}

	@Override
	public void update(String mobileno, BigDecimal amount1, String transactionType, BigDecimal amount2) {

		try (Connection con = DBUtil.getConnection()) {
			Statement stmt = con.createStatement();
			String str = "update customer set balance=" + amount1 + " where mobileno=" + mobileno;
			stmt.executeUpdate(str);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement pstm1 = con.prepareStatement("insert into transactions values(?,?,?,?)");
			pstm1.setString(1, mobileno);
			pstm1.setBigDecimal(2, amount2);
			pstm1.setString(3, transactionType);
			long millis = System.currentTimeMillis();
			java.util.Date date = new java.sql.Date(millis);
			pstm1.setDate(4, (java.sql.Date) date);
			pstm1.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ArrayList<Transactions> miniStatement(String mobileno) {
		ArrayList<Transactions> transactions = new ArrayList<Transactions>();

		try (Connection con = DBUtil.getConnection()) {
			PreparedStatement pstm = con
					.prepareStatement("SELECT  *  from transactions  WHERE transactions.mobileno = ?  ");
			pstm.setString(1, mobileno);
			ResultSet rs = pstm.executeQuery();
			if (rs.next() == false) {
				throw new SQLDataException("No transactions found");
			} else {
				transactions
						.add(new Transactions(rs.getString(1), rs.getBigDecimal(2), rs.getString(3), rs.getDate(4)));
				while (rs.next()) {
					transactions.add(
							new Transactions(rs.getString(1), rs.getBigDecimal(2), rs.getString(3), rs.getDate(4)));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

}
