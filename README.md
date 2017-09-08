# Sensor
- 센서 사용하기
```java
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  // 가속도 센서 
//        s = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); // 만보계
//        s = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);  // 기울어지는 좌/우값의 속도를 봄, 순간적인 변화를 감지할때 사용하기 좋음
//        List<Sensor>sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
//        for(Sensor s : sensors){
//            StringBuilder sb = new StringBuilder();
//            sb.append(s.getName()).append("\n");
//            sb.append(s.getType()).append("\n");
//            sb.append(s.getVendor()).append("\n");
//            sb.append(s.getMinDelay()).append("\n");
//            sb.append(s.getResolution()).append("\n\n");
//            Log.e("Sensor",sb.toString());
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,s,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    // 센서 값에 의한 변화
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        Log.e("Sensor",Arrays.toString(sensorEvent.values));
        mGraph.setPoint((int) x );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

```
