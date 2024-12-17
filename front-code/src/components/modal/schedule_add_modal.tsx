/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

import {DatePicker, message, Modal} from "antd";
import {useEffect, useState} from "react";
import {Page} from "../../models/page.ts";
import {ScheduleGroupEntity} from "../../models/entity/schedule_group_entity.ts";
import {ScheduleAddDTO} from "../../models/dto/schedule_add_dto.ts";
import dayjs from "dayjs";
import {CloseOutlined} from "@ant-design/icons";
import {AddScheduleAPI} from "../../interface/schedule_api.ts";

export function ScheduleAddModal({propOpen, groupUuid, groupList, emit, refresh}: Readonly<{
    propOpen: boolean,
    groupUuid?: string,
    groupList: Page<ScheduleGroupEntity>,
    emit: (data: boolean) => void,
    refresh: (data: boolean) => void
}>) {
    const {RangePicker} = DatePicker;

    const [open, setOpen] = useState<boolean>(false);
    const [selectedGroupUuid, setSelectedGroupUuid] = useState<string>("" as string);
    const [scheduleGroupList, setScheduleGroupList] = useState<Page<ScheduleGroupEntity>>({} as Page<ScheduleGroupEntity>);
    const [scheduleAdd, setScheduleAdd] = useState<ScheduleAddDTO>({
        add_location: false,
        custom_loop: 0,
        description: "",
        end_time: 0,
        group_uuid: "",
        loop_type: 1,
        name: "",
        priority: 3,
        start_time: 0,
        tags: [],
        type: 0,
    } as ScheduleAddDTO);
    const [hasMoreThanOneDay, setHasMoreThanOneDay] = useState<boolean>(false);
    const [changeTag, setChangeTag] = useState<string>("" as string);

    useEffect(() => {
        setOpen(propOpen);
        setScheduleGroupList(groupList);
        setScheduleAdd({
            add_location: false,
            custom_loop: 0,
            description: "",
            end_time: 0,
            group_uuid: "",
            loop_type: 1,
            name: "",
            priority: 3,
            start_time: 0,
            tags: [],
            type: 0,
        } as ScheduleAddDTO);
        if (!selectedGroupUuid) {
            if (groupUuid) {
                setSelectedGroupUuid(groupUuid);
            } else {
                setSelectedGroupUuid("");
            }
        }
    }, [propOpen]);

    useEffect(() => {
        if (scheduleAdd.type !== 0 && hasMoreThanOneDay) {
            setHasMoreThanOneDay(false);
            message.warning("循环任务不支持跨日").then(null);
            setScheduleAdd({
                ...scheduleAdd,
                start_time: 0,
                end_time: 0
            });
        }
    }, [hasMoreThanOneDay, scheduleAdd]);

    useEffect(() => {
        if (selectedGroupUuid || selectedGroupUuid !== "") {
            scheduleAdd.group_uuid = selectedGroupUuid;
            setScheduleAdd({...scheduleAdd, add_location: true});
        }
    }, [selectedGroupUuid]);

    useEffect(() => {
        setScheduleAdd({
            add_location: false,
            custom_loop: 0,
            description: "",
            end_time: 0,
            group_uuid: "",
            loop_type: 1,
            name: "",
            priority: 3,
            start_time: 0,
            tags: [],
            type: 0,
        } as ScheduleAddDTO);
    }, [refresh]);

    async function handleOk() {
        const getResp = await AddScheduleAPI(scheduleAdd);
        if (getResp?.output === "Success") {
            message.success("添加成功");
            refresh(true);
            emit(false);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return (
        <Modal
            open={open}
            title="添加日程"
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button onClick={() => emit(false)}
                            className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition">
                        取消
                    </button>
                    <button onClick={handleOk}
                            className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition">
                        完成
                    </button>
                </div>
            }
        >
            <div className="flex flex-col gap-3">
                <div className="grid grid-cols-2 gap-2">
                    <div className={"col-span-full"}>
                        <label htmlFor="group-list"
                               className="font-medium flex space-x-0.5">
                            <span className={"text-sm"}>添加于</span>
                            <span className={"text-xs text-red-500"}>*</span>
                        </label>
                        <select
                            name="group-list"
                            id="group-list"
                            value={selectedGroupUuid}
                            onChange={(e) => setSelectedGroupUuid(e.target.value)}
                            className="mt-1 w-full rounded-md border-gray-300 text-gray-700 sm:text-sm"
                        >
                            <option value="">个人</option>
                            {
                                scheduleGroupList.records?.map((item) => (
                                    <option key={item.group_uuid} value={item.group_uuid}>{item.name}</option>
                                ))
                            }
                        </select>
                    </div>
                    <div>
                        <label htmlFor="schedule_name" className="flex space-x-0.5">
                            <span className={"text-sm font-medium text-gray-800"}>日程名字</span>
                            <span className={"text-xs text-red-500"}>*</span>
                        </label>
                        <input
                            type="text"
                            id="schedule_name"
                            value={scheduleAdd?.name}
                            onChange={(event) => setScheduleAdd({...scheduleAdd, name: event.currentTarget.value})}
                            placeholder="监控系统维护"
                            className="mt-1 w-full rounded-md border-gray-300 text-gray-800 shadow-sm sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="group-priority"
                               className="font-medium flex space-x-0.5">
                            <span className={"text-sm"}>添加于</span>
                            <span className={"text-xs text-red-500"}>*</span>
                        </label>
                        <select
                            name="group-priority"
                            id="group-priority"
                            value={scheduleAdd?.priority}
                            onChange={(e) => setScheduleAdd({...scheduleAdd, priority: Number(e.target.value)})}
                            className="mt-1 w-full rounded-md border-gray-300 text-gray-700 sm:text-sm"
                        >
                            <option value={4}>重要</option>
                            <option value={3}>一般</option>
                            <option value={2}>次要</option>
                            <option value={1}>不重要</option>
                        </select>
                    </div>
                    <div>
                        <label htmlFor="schedule-type" className="flex space-x-0.5">
                            <span className={"text-sm font-medium text-gray-800"}>日程类型</span>
                            <span className={"text-xs text-red-500"}>*</span>
                        </label>
                        <select
                            name="group-type"
                            id="group-type"
                            value={scheduleAdd?.type}
                            onChange={(e) => {
                                setScheduleAdd({...scheduleAdd, type: Number(e.target.value)});
                            }}
                            className="mt-1 w-full rounded-md border-gray-300 text-gray-700 sm:text-sm"
                        >
                            <option value={0}>单次任务</option>
                            <option value={1}>循环任务</option>
                            <option value={2}>一日任务</option>
                        </select>
                    </div>
                    <div className={`${(scheduleAdd?.type !== 1 ? "hidden" : null)}`}>
                        <label htmlFor="schedule-type" className="flex space-x-0.5">
                            <span className={"text-sm font-medium text-gray-800"}>循环类型</span>
                            <span className={"text-xs text-red-500"}>*</span>
                        </label>
                        <select
                            name="group-type"
                            id="group-type"
                            value={scheduleAdd?.loop_type}
                            onChange={(e) => setScheduleAdd({...scheduleAdd, loop_type: Number(e.target.value)})}
                            className="mt-1 w-full rounded-md border-gray-300 text-gray-700 sm:text-sm"
                        >
                            <option value={1}>每日</option>
                            <option value={2}>每周周一</option>
                            <option value={3}>每个工作日</option>
                            <option value={4}>每个月一号</option>
                            <option value={5}>每个月十四号</option>
                            <option value={0}>自定义</option>
                        </select>
                    </div>
                    <div className={`${(scheduleAdd?.loop_type !== 0 || scheduleAdd?.type !== 1 ? "hidden" : null)}`}>
                        <label htmlFor="schedule_name" className="flex space-x-0.5">
                            <span className={"text-sm font-medium text-gray-800"}>自定义几天循环</span>
                            <span className={"text-xs text-red-500"}>*</span>
                        </label>
                        <input
                            type="number"
                            id="schedule_name"
                            value={scheduleAdd?.custom_loop}
                            onChange={(event) => setScheduleAdd({
                                ...scheduleAdd,
                                custom_loop: Number(event.currentTarget.value)
                            })}
                            placeholder="2"
                            className="mt-1 w-full rounded-md border-gray-300 text-gray-800 shadow-sm sm:text-sm"
                        />
                    </div>
                    <div className={`col-span-full ${scheduleAdd?.type === 2 ? "hidden" : null}`}>
                        <label htmlFor="group-list"
                               className="font-medium flex space-x-0.5">
                            <span className={"text-sm"}>日程时间</span>
                            <span className={"text-xs text-red-500"}>*</span>
                        </label>
                        <RangePicker
                            showTime={{format: 'HH:mm'}}
                            className={"mt-1 py-1.5 w-full rounded-md border-gray-300 text-gray-700 sm:text-sm"}
                            format="YYYY-MM-DD HH:mm"
                            value={[scheduleAdd?.start_time ? dayjs(scheduleAdd.start_time) : undefined, scheduleAdd?.end_time ? dayjs(scheduleAdd.end_time) : undefined]}
                            onChange={(_, dateString) => {
                                setHasMoreThanOneDay(dayjs(dateString[1]).diff(dayjs(dateString[0]), 'day') > 0);
                                console.log(dayjs(dateString[1]).diff(dayjs(dateString[0]), 'day') > 0);
                                if (scheduleAdd?.type === 0) {
                                    // 时间需要转为时间戳
                                    setScheduleAdd({
                                        ...scheduleAdd,
                                        start_time: dayjs(dateString[0]).valueOf(),
                                        end_time: dayjs(dateString[1]).valueOf()
                                    });
                                } else {
                                    message.warning("循环任务不支持跨日").then();
                                    setScheduleAdd({...scheduleAdd});
                                }
                            }}
                        />
                    </div>
                    <div className={"col-span-full"}>
                        <label htmlFor="group-tags" className="flex font-medium text-gray-700 gap-1">
                            <span>小组标签</span>
                            <span className={"text-red-500 text-xs"}>*</span>
                        </label>
                        <div className={"flex gap-1 mb-1"}>
                            <input
                                type="text"
                                id="group-tags"
                                onChange={(event) => setChangeTag(event.currentTarget.value)}
                                placeholder="宿舍"
                                className="transition w-full rounded-md border-gray-300 shadow-sm sm:text-sm"
                            />
                            <button
                                className={"flex-shrink-0 py-1.5 px-4 rounded-md text-white shadow bg-sky-500 hover:bg-sky-600 transition"}
                                onClick={async () => {
                                    if (changeTag.trim() !== "") {
                                        if (scheduleAdd.tags === undefined) {
                                            scheduleAdd.tags = [changeTag.trim()];
                                        } else if (!scheduleAdd.tags.includes(changeTag.trim())) {
                                            scheduleAdd.tags?.push(changeTag.trim());
                                        } else {
                                            message.warning("标签已存在");
                                        }
                                        setScheduleAdd({...scheduleAdd});
                                    } else {
                                        message.warning("标签不能为空");
                                    }
                                }}>
                                添加
                            </button>
                        </div>
                        <div className={"flex gap-1 flex-wrap break-words"}>
                            {
                                scheduleAdd.tags?.map((tag, index) => (
                                    <div key={`tag-${tag}-${index}`}
                                         className={"flex-shrink-0 transition bg-gray-100 text-gray-600 px-3 py-1 rounded-md justify-center flex space-x-1"}>
                                        <span>#{tag}</span>
                                        <CloseOutlined className={"transition text-gray-600 hover:text-gray-800"}
                                                       onClick={() => {
                                                           scheduleAdd.tags?.splice(index, 1);
                                                           setScheduleAdd({...scheduleAdd});
                                                       }}/>
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                    <div className={"col-span-full"}>
                        <label htmlFor="group-tags" className="flex font-medium text-gray-700 gap-1">
                            <span>具体描述</span>
                            <span className={"text-red-500 text-xs"}>*</span>
                        </label>
                        <textarea
                            id="group-tags"
                            onChange={(event) => setScheduleAdd({
                                ...scheduleAdd,
                                description: event.currentTarget.value
                            })}
                            placeholder="对监控系统进行维护操作"
                            className="transition w-full rounded-md border-gray-300 shadow-sm sm:text-sm"
                        />
                    </div>
                </div>
            </div>
        </Modal>
    )
}
