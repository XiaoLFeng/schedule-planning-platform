import {useEffect, useState} from "react";
import {Form, Input, Modal} from "antd";
import dayjs from "dayjs"; // 引入 dayjs
import isoWeek from "dayjs/plugin/isoWeek";
import {ViewTimeWeekDTO} from "../../../models/dto/view_time_week_dto.ts"; // ISO 周插件

dayjs.extend(isoWeek); // 启用 ISO 周插件

export function DashboardViewWeek() {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedTimeRange, setSelectedTimeRange] = useState<ViewTimeWeekDTO>({} as ViewTimeWeekDTO);
    const [form] = Form.useForm();
    const [draggingRange, setDraggingRange] = useState<ViewTimeWeekDTO>({} as ViewTimeWeekDTO);
    const [isDragging, setIsDragging] = useState(false);
    const [currentDay, setCurrentDay] = useState<dayjs.Dayjs>(); // 记录当前拖拽的天
    const [weekDays, setWeekDays] = useState<Array<dayjs.Dayjs>>([]);

    const timeSlots = Array.from({length: 24}, (_, i) => `${String(i).padStart(2, "0")}:00`);

    useEffect(() => {
        // 初始化本周的日期（周一到周日）
        const startOfWeek = dayjs().startOf("isoWeek");
        const days = Array.from({length: 7}, (_, i) =>
            startOfWeek.add(i, "day")
        );
        setWeekDays(days);
    }, []);

    const openCreateModal = () => {
        setIsModalVisible(true);
        setSelectedTimeRange(draggingRange);
    };

    const handleOk = () => {
        form.validateFields().then((values) => {
            console.log("创建事件:", values, selectedTimeRange);
            setIsModalVisible(false);
        });
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const handleMouseDown = (day: dayjs.Dayjs, time: string) => {
        setDraggingRange({
            start: {
                day: day.format("YYYY-MM-DD"),
                time: dayjs(time, "HH:mm").format("HH:mm"),
            }
        } as ViewTimeWeekDTO);
        setIsDragging(true); // 开始拖拽
        setCurrentDay(day); // 设置当前拖拽的天
    };

    const handleMouseMove = (day: dayjs.Dayjs, time: string) => {
        if (isDragging && day.isSame(currentDay, "day")) {
            setDraggingRange((prev) => ({
                ...prev,
                end: {
                    day: day.format("YYYY-MM-DD"),
                    time: dayjs(time, "HH:mm").format("HH:mm"),
                },
            }));
        }
    };

    const handleMouseUp = (day: dayjs.Dayjs, time: string) => {
        if (isDragging && day.isSame(currentDay, "day")) {
            setDraggingRange((prev) => ({
                ...prev,
                end: {
                    day: day.format("YYYY-MM-DD"),
                    time: dayjs(time, "HH:mm").format("HH:mm"),
                },
            }));
            setIsDragging(false);
            openCreateModal();
        }
    };

    const isTimeSelected = (day: dayjs.Dayjs, time: string) => {
        if (!draggingRange.start || !draggingRange.end || !day.isSame(currentDay, "day")) return false;

        const startTime = parseInt(draggingRange.start.time.split(":")[0]);
        const endTime = parseInt(draggingRange.end.time.split(":")[0]);
        const currentTime = parseInt(time.split(":")[0]);

        return currentTime >= Math.min(startTime, endTime) && currentTime <= Math.max(startTime, endTime);
    };


    return (
        <div className={"bg-white rounded-lg shadow p-6"}>
            <div className="w-full h-full">
                <div className="grid grid-cols-8 h-full border-t border-l border-gray-200 rounded-md">
                    <div className="border-r border-b border-gray-200">
                        <div className="bg-gray-100 p-2 text-center font-bold">时间</div>
                        {timeSlots.map((time, index) => (
                            <div key={index} className="h-16 border-b border-gray-200 text-center">
                                {time}
                            </div>
                        ))}
                    </div>
                    {weekDays.map((day, dayIndex) => (
                        <div key={dayIndex} className="border-r border-b border-gray-200">
                            <div className="bg-gray-100 p-2 text-center font-bold">
                                {`周${"一二三四五六日"[dayIndex]} (${day.format("DD")})`}
                            </div>
                            <div className="relative h-full">
                                {timeSlots.map((time, index) => (
                                    <div
                                        key={index}
                                        className={`h-16 cursor-pointer ${
                                            isTimeSelected(day, time) ? "bg-blue-100" : "border-gray-200"
                                        }`}
                                        onMouseDown={() => handleMouseDown(day, time)}
                                        onMouseMove={() => handleMouseMove(day, time)}
                                        onMouseUp={() => handleMouseUp(day, time)}
                                    >
                                        <div className="h-full"></div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>

                <Modal
                    title="创建新事件"
                    open={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                >
                    <Form form={form}>
                        <Form.Item
                            name="eventName"
                            label="事件名称"
                            rules={[{required: true, message: "请输入事件名称"}]}
                        >
                            <Input placeholder="请输入事件名称"/>
                        </Form.Item>
                        <Form.Item name="eventDescription" label="事件描述">
                            <Input.TextArea placeholder="请输入事件描述"/>
                        </Form.Item>
                    </Form>
                    {selectedTimeRange && (
                        <div className="mt-4">
                            <p>
                                已选时间段: {selectedTimeRange?.start?.day} 从{" "}
                                {selectedTimeRange?.start?.time} 到 {selectedTimeRange?.end?.time}
                            </p>
                        </div>
                    )}
                </Modal>
            </div>
        </div>
    );
}
