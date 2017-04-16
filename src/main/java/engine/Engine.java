package engine;


public class Engine implements Runnable {

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private final Window mWindow;

    private final Thread mGameLoopThread;

    private final Timer mTimer;

    private final GameLogic mGameLogic;

    private final MouseInput mMouseInput;

    public Engine(String windowTitle, int width, int height, boolean vSync, GameLogic gameLogic) throws Exception {
        mGameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        mWindow = new Window(windowTitle, width, height, vSync);
        this.mGameLogic = gameLogic;
        mMouseInput = new MouseInput();
        mTimer = new Timer();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            mGameLoopThread.run();
        } else {
            mGameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        mWindow.init();
        mTimer.init();
        mGameLogic.init(mWindow);
        mMouseInput.init(mWindow);
    }

    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !mWindow.windowShouldClose()) {
            elapsedTime = mTimer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if (!mWindow.isvSync()) {
                sync();
            }
        }
    }

    protected void cleanup() {
        mGameLogic.cleanup();
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = mTimer.getLastLoopTime() + loopSlot;
        while (mTimer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

    protected void input() {
        mMouseInput.input(mWindow);
        mGameLogic.input(mWindow, mMouseInput);
    }

    protected void update(float interval) {
        mGameLogic.update(interval, mMouseInput);
    }

    protected void render() {
        mGameLogic.render(mWindow);
        mWindow.update();
    }
}