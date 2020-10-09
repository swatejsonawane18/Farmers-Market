import java.util.concurrent.locks.ReentrantLock;

public class Locks {

    int count = 0;

    static void setLock(ReentrantLock lock) {
        lock.lock();
    }

    static void removeLock(ReentrantLock lock) {
        lock.unlock();
    }

    static boolean checkLock(ReentrantLock lock) {
        return lock.isLocked();
    }

}



