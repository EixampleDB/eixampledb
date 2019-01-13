package com.eixampledb.core;

import com.eixampledb.core.api.EixampleDbEntry;
import com.eixampledb.core.api.ValueType;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Snapshot implements Runnable {

    private String fileName;
    private ConcurrentMap<String, EixampleDbEntry> db;
    public static boolean isLoadingServiceSnapshots = false;

    /**
     *
     * @param fileName File path or name
     * @param db ConcurrentMap<String, EixampleDbEntry>
     */
    public Snapshot(String fileName, ConcurrentMap<String, EixampleDbEntry> db) {
        this.fileName = fileName;
        this.db = db;
    }


    @Override
    public void run() {
        // Make snapshot every 5 seconds
        while(true) {
            lock_and_write(this.fileName, this.db);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a stream and writes a file in JSON format given a ConcurrentMap
     * @param fileName Name of the file to write
     * @param db The database to save
     * @throws IOException Lock already taken
     */
    public void lock_and_write(String fileName, ConcurrentMap<String, EixampleDbEntry> db) {

        // If empty database
        if (db.size() < 1) return;

        // create a stream
        RandomAccessFile stream = null;

        // accessing file
        try {
            stream = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be created nor found");
            e.printStackTrace();
        }

        // create a channel for the stream
        FileChannel channel = stream.getChannel();

        // try to lock the channel
        FileLock lock = null;
        try {
            lock = channel.tryLock();
        } catch (final OverlappingFileLockException e) {
            try {
                stream.close();
            } catch (IOException e1) {
                System.out.println("Stream cannot be closed when overlapping");
                e1.printStackTrace();
            }
            try {
                channel.close();
            } catch (IOException e1) {
                System.out.println("Channel cannot be closed when overlapping");
                e1.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error in lock is not from Overlapping, go suicide");
            e.printStackTrace();
        }

        JSONObject finalJSON = new JSONObject();

        // Iterate database and create json
        for (ConcurrentHashMap.Entry<String, EixampleDbEntry> entry : db.entrySet())
        {
            JSONObject json = new JSONObject();
            json.put("key", entry.getValue().getKey());
            json.put("value", entry.getValue().getValue());
            json.put("creationTimestamp", entry.getValue().getCreationTimestamp());
            json.put("lastUpdateTimestamp", entry.getValue().getLastupdateTimestamp());
            json.put("type", entry.getValue().getType());

            finalJSON.put(entry.getKey(), json);
        }

        // Writing file
        try {
            stream.write(finalJSON.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // unlock
        try {
            lock.release();
        } catch (IOException e) {
            System.out.println("Lock cannot be released");
            e.printStackTrace();
        }

        // close stream
        try {
            stream.close();
        } catch (IOException e) {
            System.out.println("Stream cannot be closed when lock released");
            e.printStackTrace();
        }

        // close channel
        try {
            channel.close();
        } catch (IOException e) {
            System.out.println("Channel cannot be closed when lock released");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param path Path of the file to load
     * @return ConcurrentHashMap(String, EixampleDbEntry)
     */
    public ConcurrentMap<String, EixampleDbEntry> lock_and_read(String path, ConcurrentMap<String, EixampleDbEntry> db) {

        FileReader fr = null;
        isLoadingServiceSnapshots = true;

        // Read file
        try {
            fr = new FileReader((path));
        } catch (FileNotFoundException e) {
            System.out.println("Snapshot not found");
            isLoadingServiceSnapshots=false;
        }

        // Parse the file and obtain fields
        JSONObject jsonObject = new JSONObject();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(fr);
            jsonObject = (JSONObject) obj;

        } catch (Exception e) {
            // pass (Snapshot not found)
            isLoadingServiceSnapshots = false;
        }

        EixampleDbEntry entry;
        Set keys = jsonObject.keySet();

        // Putting json objects read from file into db
        for (Object k: keys) {
            // Get values for every key
            JSONObject j2 = (JSONObject) jsonObject.get(k);
            entry = new EixampleDbEntry(j2.get("key").toString(),
                    j2.get("value"),
                    (long) j2.get("creationTimestamp"),
                    (long) j2.get("lastUpdateTimestamp"),
                    j2.get("type").equals("STR") ? ValueType.STR : ValueType.NUM);
            db.put(k.toString(), entry);
        }

        isLoadingServiceSnapshots = false;

        return db;
    }
}
