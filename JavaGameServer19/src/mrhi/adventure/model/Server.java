package mrhi.adventure.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import mrhi.adventure.control.ReceiveHandler;
import mrhi.adventure.control.MobGenManager;
import mrhi.adventure.control.SendHandler;
import mrhi.adventure.db.AccountDAO;
import mrhi.adventure.db.CharacterDAO;
import mrhi.adventure.db.ChatDAO;
import mrhi.adventure.db.ItemDAO;
import mrhi.adventure.model.packet.MyPacket;

public class Server {

	private static Server theInstance = new Server();
	private List<Player> playerList = new LinkedList<>();
	private GameManager gameManager = new GameManager();
	private CharacterManager characterManager = new CharacterManager();
	private AccountManager accountManager = new AccountManager();
	private SendHandler sendHandler;

	public static Server getInstance() {
		return theInstance;
	}

	private Server() {
		ServerThread st = new ServerThread();
		Thread sThread = new Thread(st);
		sThread.start();
		
		sendHandler = new SendHandler();
		new Thread(new MobGenManager()).start();
		new Thread(sendHandler).start();
	}
	
	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public CharacterManager getCharacterManager() {
		return characterManager;
	}

	public void setCharacterManager(CharacterManager characterManager) {
		this.characterManager = characterManager;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}	

	public void addPacket(Player player, MyPacket packet) {
		this.sendHandler.addPacket(player, packet);
	}
	
	public void addPacket(List<Player> players, MyPacket packet) {
		this.sendHandler.addPacket(players, packet);
	}
	
	public SendHandler getSendHandler() {
		return sendHandler;
	}

	public void setSendHandler(SendHandler sendHandler) {
		this.sendHandler = sendHandler;
	}

	public void setPlayerList(List<Player> mySessionList) {
		this.playerList = mySessionList;
	}

	public class ServerThread implements Runnable {

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(21212);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true)
			{
				try {
					System.out.println("연결된 사람 수 : " + playerList.size());
					Socket socket = serverSocket.accept();
					Player ms = new Player(socket);
					playerList.add(ms);
					ReceiveHandler msr = new ReceiveHandler(ms);
					Thread sThread = new Thread(msr);
					sThread.start();
					System.out.println(socket.getInetAddress().getHostAddress() + " 소켓 연결 성공!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void main(String[] args) {
		Server.getInstance();
	}
}
