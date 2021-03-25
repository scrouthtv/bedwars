package me.scrouthtv.maps;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DimCloner {
	
	/**
	 * cloneDim clones a world by copying the world's data folder.
	 *
	 * @param w The world to clone.
	 * @param target How the clone should be named.
	 * @return the clone of the world or null if an error occurred.
	 */
	@Nullable
	static World cloneDim(World w, String target) {
		try {
			copyWorldTree(w.getWorldFolder(), new File(Bukkit.getWorldContainer(), target));
			return Bukkit.createWorld(new WorldCreator(target));
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private static final List<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
	
	// https://bukkit.org/threads/unload-delete-copy-worlds.182814/
	private static void copyWorldTree(File source, File target) throws IOException {
		if (ignore.contains(source.getName()))
			return;
		
		if (source.isDirectory()) {
			if (!target.exists() && !target.mkdirs()) throw new IOException("Couldn't create world directory!");
			
			String files[] = source.list();
			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(target, file);
				copyWorldTree(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(target);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0)
				out.write(buffer, 0, length);
			
			in.close();
			out.close();
		}
	}
}
