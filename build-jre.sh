#!/bin/bash
jlink \
  -p 'target/libs-mp' \
  --add-modules java.base,java.instrument,java.logging,java.management,java.naming,java.sql,javafx.base,javafx.controls,javafx.fxml,javafx.graphics \
  --output target/jre \
  --strip-debug --no-man-pages --no-header-files
cp -r target/libs-cp target/jre/
cp target/*.jar target/jre/libs-cp/
cat > target/jre/bin/run << 'EOF'
#!/bin/bash
"$(dirname "$0")/java" \
  -cp "$(dirname $0)"/../libs-cp/'*' \
  -Dapp.jdbc.url=jdbc:h2:/home/philip/code/databases/task-db \
  com.github.phoswald.sample.Application
EOF
chmod a+x target/jre/bin/run
